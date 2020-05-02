package com.example.contactsretriever.contactlist

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.contactsretriever.Contact

class ContactListViewModel(application: Application) : ViewModel() {

    private val TAG = "ContactList"

    private val contentResolver: ContentResolver = application.contentResolver
    val context: Context = application.applicationContext

    fun fetchContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        // Configure URI to search in the local directory
        var uri = ContactsContract.Contacts.CONTENT_URI
        uri = uri.buildUpon().appendQueryParameter(
            ContactsContract.DIRECTORY_PARAM_KEY, ContactsContract.Directory.DEFAULT.toString()
        ).build()

        val contactsCursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        if (contactsCursor != null && contactsCursor.count > 0) {
            val idIndex = contactsCursor
                .getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = contactsCursor
                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val photoIndex = contactsCursor
                .getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
            val ringtoneIndex = contactsCursor
                .getColumnIndex(ContactsContract.Contacts.CUSTOM_RINGTONE)

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex).toInt()
                val name = contactsCursor.getString(nameIndex)
                val photoUri = contactsCursor.getString(photoIndex) ?: ""
                val ringtoneUri = contactsCursor.getString(ringtoneIndex) ?: ""

                contacts.add(Contact(id, name, photoUri, ringtoneUri))
                Log.i(TAG, "ContactInfo:\n${contacts.last()}")
            }

            contactsCursor.close()
        }

        return contacts
    }
}