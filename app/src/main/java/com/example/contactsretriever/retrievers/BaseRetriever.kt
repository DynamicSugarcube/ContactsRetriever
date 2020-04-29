package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract

abstract class BaseRetriever(protected val contentResolver: ContentResolver) {

    abstract fun retrieve(contactId: String): Any

    companion object {
        fun getField(cursor: Cursor, field: String): String? =
            cursor.getString(cursor.getColumnIndex(field))
    }

    protected fun obtainCursor(selectionArgs: Array<String>) =
        contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            ContactsContract.Data.MIMETYPE + " = ? AND " +
                    ContactsContract.Data.CONTACT_ID + " = ?",
            selectionArgs,
            null
        )
}