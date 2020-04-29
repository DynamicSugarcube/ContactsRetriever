package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.provider.ContactsContract

class NamesRetriever(contentResolver: ContentResolver) : BaseRetriever(contentResolver) {

    override fun retrieve(contactId: String): Pair<String, String> {
        var firstName = String()
        var lastName = String()

        val cursor = obtainCursor(
            arrayOf(
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                contactId
            )
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                firstName = getField(
                    cursor,
                    ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
                ) ?: ""
                lastName = getField(
                    cursor,
                    ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
                ) ?: ""
            }
        }

        cursor?.close()
        return firstName to lastName
    }
}