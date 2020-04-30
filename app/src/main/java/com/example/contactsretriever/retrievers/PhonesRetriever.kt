package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.provider.ContactsContract
import com.example.contactsretriever.Contact

class PhonesRetriever(contentResolver: ContentResolver) : BaseRetriever(contentResolver) {

    override suspend fun retrieve(contactId: String): MutableList<Contact.Phone> {
        val phones = mutableListOf<Contact.Phone>()

        val cursor = obtainCursor(
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE
            ),
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                contactId
            )
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val phoneNumber = getField(cursor, ContactsContract.CommonDataKinds.Phone.NUMBER)
                val type = when (getField(
                    cursor,
                    ContactsContract.CommonDataKinds.Phone.TYPE
                )?.toInt()) {
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                        Contact.Phone.Type.WORK
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ->
                        Contact.Phone.Type.MOBILE
                    else ->
                        Contact.Phone.Type.OTHER
                }
                if (phoneNumber != null) {
                    phones.add(Contact.Phone(phoneNumber, type))
                }
            }
        }

        cursor?.close()
        return phones
    }
}