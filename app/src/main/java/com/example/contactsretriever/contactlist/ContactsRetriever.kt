package com.example.contactsretriever.contactlist

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.example.contactsretriever.Contact
import kotlinx.coroutines.*

class ContactsRetriever(private val contentResolver: ContentResolver) {

    private val TAG = "ContactsRetriever"

    suspend fun fetchContacts() = withContext(Dispatchers.IO) {
            async { retrieveContacts() }
            async { retrievePhones() }
            async { retrieveEmails() }
            async { retrieveGroups() }
    }

    private suspend fun retrieveContacts() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Retrieving contacts...")

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

        if (cursor != null && cursor.count > 0) {
            val idIndex = cursor
                .getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor
                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val photoIndex = cursor
                .getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
            val ringtoneIndex = cursor
                .getColumnIndex(ContactsContract.Contacts.CUSTOM_RINGTONE)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idIndex)
                val name = cursor.getString(nameIndex)
                val photoUri = cursor.getString(photoIndex) ?: ""
                val ringtoneUri = cursor.getString(ringtoneIndex) ?: ""
                val contact = Contact(
                    id,
                    name,
                    photoUri,
                    ringtoneUri
                )

                ContactsRepo.contacts.add(contact)
            }

            cursor.close()
        }

        Log.d(TAG, "Contacts retrieving is done")
    }

    private suspend fun retrievePhones() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Retrieving phones...")

        val cursor: Cursor? = getCursorByType(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )

        if (cursor != null && cursor.count > 0) {
            val contactIdIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val typeIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)

            while (cursor.moveToNext()) {
                val contactId = cursor.getString(contactIdIndex)
                val number = cursor.getString(numberIndex)
                val type = cursor.getString(typeIndex)
                val phone = Contact.Phone(
                    number,
                    when (type.toInt()) {
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                            Contact.Phone.Type.WORK
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ->
                            Contact.Phone.Type.MOBILE
                        else -> Contact.Phone.Type.OTHER
                    }
                )

                if (ContactsRepo.phonesHashMap.containsKey(contactId)) {
                    ContactsRepo.phonesHashMap[contactId]?.add(phone)
                } else {
                    ContactsRepo.phonesHashMap[contactId] = mutableListOf(phone)
                }
            }

            cursor.close()
        }

        Log.d(TAG, "Phones retrieving is done")
    }

    private suspend fun retrieveEmails() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Retrieving emails...")

        val cursor: Cursor? = getCursorByType(
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
        )

        if (cursor != null && cursor.count > 0) {
            val contactIdIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID)
            val emailIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)

            while (cursor.moveToNext()) {
                val contactId = cursor.getString(contactIdIndex)
                val email = cursor.getString(emailIndex)

                if (ContactsRepo.emailsHashMap.containsKey(contactId)) {
                    ContactsRepo.emailsHashMap[contactId]?.add(email)
                } else {
                    ContactsRepo.emailsHashMap[contactId] = mutableListOf(email)
                }
            }

            cursor.close()
        }

        Log.d(TAG, "Emails retrieving is done")
    }

    private suspend fun retrieveGroups() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Retrieving groups...")

        val cursor: Cursor? = getCursorByType(
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
        )

        if (cursor != null && cursor.count > 0) {
            val contactIdIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID)
            val groupIdIndex = cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID)

            while (cursor.moveToNext()) {
                val contactId = cursor.getString(contactIdIndex)
                val groupId = cursor.getString(groupIdIndex)

                if (ContactsRepo.groupsMembershipHashMap.containsKey(contactId)) {
                    ContactsRepo.groupsMembershipHashMap[contactId]?.add(groupId)
                } else {
                    ContactsRepo.groupsMembershipHashMap[contactId] = mutableListOf(groupId)
                }
            }

            cursor.close()
        }

        Log.d(TAG, "Groups retrieving is done")
    }

    private fun getCursorByType(type: String): Cursor? = contentResolver.query(
        ContactsContract.Data.CONTENT_URI,
        null,
        ContactsContract.Data.MIMETYPE + " = ?",
        arrayOf(type),
        null
    )
}