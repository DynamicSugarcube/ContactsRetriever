package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.provider.ContactsContract

class EmailsRetriever(contentResolver: ContentResolver) : BaseRetriever(contentResolver) {

    override fun retrieve(contactId: String): MutableList<String> {
        val emails = mutableListOf<String>()

        val cursor = obtainCursor(
            arrayOf(
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                contactId
            )
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val email = getField(cursor, ContactsContract.CommonDataKinds.Email.ADDRESS)
                if (email != null) {
                    emails.add(email)
                }
            }
        }

        cursor?.close()
        return emails
    }
}