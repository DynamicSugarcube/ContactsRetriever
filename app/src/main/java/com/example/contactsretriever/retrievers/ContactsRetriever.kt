package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.contactsretriever.Contact
import kotlinx.coroutines.*

class ContactsRetriever(context: Context) {

    private val TAG = "ContactsRetriever"

    private val contentResolver: ContentResolver = context.contentResolver

    suspend fun retrieve(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        // Configure URI to search in the local directory
        var uri = ContactsContract.Contacts.CONTENT_URI
        uri = uri.buildUpon().appendQueryParameter(
            ContactsContract.DIRECTORY_PARAM_KEY, ContactsContract.Directory.DEFAULT.toString()
        ).build()

        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.CUSTOM_RINGTONE
        )

        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val id = BaseRetriever.getField(cursor, ContactsContract.Contacts._ID) ?: continue

                var firstName = ""
                var lastName = ""
                var phones = mutableListOf<Contact.Phone>()
                var emails = mutableListOf<String>()
                var groups = hashMapOf<String, String>()
                var ringerUri = ""
                var photoUri = ""

                runBlocking {
                    val taskNames = async(Dispatchers.IO) {
                        val namesPair = NamesRetriever(contentResolver).retrieve(id)
                        firstName = namesPair.first
                        lastName = namesPair.second
                    }

                    val taskPhones = async(Dispatchers.IO) {
                        phones = PhonesRetriever(contentResolver).retrieve(id)
                    }

                    val taskEmails = async(Dispatchers.IO) {
                        emails = EmailsRetriever(contentResolver).retrieve(id)
                    }

                    val taskGroups = async(Dispatchers.IO) {
                        groups = GroupsRetriever(contentResolver).retrieve(id)
                    }

                    val taskRinger = async(Dispatchers.IO) {
                        ringerUri = RingtoneRetriever.retrieveRingtoneUri(
                            BaseRetriever.getField(
                                cursor,
                                ContactsContract.Contacts.CUSTOM_RINGTONE
                            )
                        )
                    }

                    val taskPhoto = async(Dispatchers.IO) {
                        photoUri = PhotoRetriever.retrievePhotoUri(id)
                    }
                }

                val contact = Contact(
                    id.toInt(),
                    firstName,
                    lastName,
                    phones,
                    emails,
                    groups,
                    ringerUri,
                    photoUri
                )

                Log.i(TAG, "Fetch a contact:\n$contact")
                contacts.add(contact)
            }
        }

        cursor?.close()
        return contacts
    }
}