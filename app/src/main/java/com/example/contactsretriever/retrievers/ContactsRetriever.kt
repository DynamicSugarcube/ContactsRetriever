package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.contactsretriever.Contact

class ContactsRetriever(context: Context) {

    private val TAG = "ContactsRetriever"

    private val contentResolver: ContentResolver = context.contentResolver

    fun retrieve(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        // Configure URI to search in the local directory
        var uri = ContactsContract.Contacts.CONTENT_URI
        uri = uri.buildUpon().appendQueryParameter(
            ContactsContract.DIRECTORY_PARAM_KEY, ContactsContract.Directory.DEFAULT.toString()
        ).build()

        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val id = BaseRetriever.getField(cursor, ContactsContract.Contacts._ID) ?: continue

                val (firstName, lastName) = NamesRetriever(contentResolver).retrieve(id)

                val phones = PhonesRetriever(contentResolver).retrieve(id)

                val emails = EmailsRetriever(contentResolver).retrieve(id)

                val groups = GroupsRetriever(contentResolver).retrieve(id)

                val ringerUri = RingtoneRetriever.retrieveRingtoneUri(
                    BaseRetriever.getField(cursor, ContactsContract.Contacts.CUSTOM_RINGTONE)
                )

                val photoUri = PhotoRetriever.retrievePhotoUri(id)

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