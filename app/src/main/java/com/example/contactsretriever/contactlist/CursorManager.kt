package com.example.contactsretriever.contactlist

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CursorManager(private val contentResolver: ContentResolver) {

    private var phonesCursor: Cursor? = null
    private var emailsCursor: Cursor? = null
    private var groupsCursor: Cursor? = null

    init {
        runBlocking {
            phonesCursor = getCursorByType(
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )

            emailsCursor = getCursorByType(
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )

            groupsCursor = getCursorByType(
                ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
            )
        }
    }

    private fun getCursorByType(type: String): Cursor? = contentResolver.query(
        ContactsContract.Data.CONTENT_URI,
        null,
        ContactsContract.Data.MIMETYPE + " = ?",
        arrayOf(type),
        null
    )
}