package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.example.contactsretriever.Contact

class ContactsRetriever(context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    fun retrieve(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
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

                contacts.add(contact)
            }
        }

        cursor?.close()
        return contacts
    }
}