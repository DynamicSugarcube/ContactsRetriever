package com.example.contactsretriever

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log

class ContactsRetriever(private val contentResolver: ContentResolver) {

    private val TAG = "ContactsRetriever"

    fun getContacts() {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                var contactId: Int = -1
                var name: String = ""
                val phones: MutableList<Contact.Phone> = mutableListOf()
                val emails: MutableList<String> = mutableListOf()

                var tempCursor: Cursor?

                val id = getField(cursor, ContactsContract.Contacts._ID)
                contactId = id.toInt()

                name = getField(cursor, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

                /**
                 * Fetch phones
                 */
                tempCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(id),
                    null
                )

                if (tempCursor?.count ?: 0 > 0) {
                    while (tempCursor != null && tempCursor.moveToNext()) {
                        val phone = getField(tempCursor, ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val type = when(getField(tempCursor, ContactsContract.CommonDataKinds.Phone.TYPE).toInt()) {
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK ->
                                Contact.Phone.Type.WORK
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE ->
                                Contact.Phone.Type.MOBILE
                            else ->
                                Contact.Phone.Type.OTHER
                        }
                        phones.add(Contact.Phone(phone, type))
                    }
                }

                tempCursor?.close()

                /**
                 * Fetch emails
                 */
                tempCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    arrayOf(id),
                    null
                )

                if (tempCursor?.count ?: 0 > 0) {
                    while (tempCursor != null && tempCursor.moveToNext()) {
                        val email = getField(tempCursor, ContactsContract.CommonDataKinds.Email.ADDRESS)
                        emails.add(email)
                    }
                }

                tempCursor?.close()

                val contact = Contact(contactId,
                    name,
                    phones,
                    emails)
                Log.i(TAG, "Contact info:\n $contact")
            }
        }

        cursor?.close()
    }

    private fun getField(cursor: Cursor, field: String): String {
        return cursor.getString(cursor.getColumnIndex(field))
    }
}