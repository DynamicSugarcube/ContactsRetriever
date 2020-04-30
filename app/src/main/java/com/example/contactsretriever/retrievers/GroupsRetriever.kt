package com.example.contactsretriever.retrievers

import android.content.ContentResolver
import android.provider.ContactsContract

class GroupsRetriever(contentResolver: ContentResolver) : BaseRetriever(contentResolver) {

    override suspend fun retrieve(contactId: String): HashMap<String, String> {
        val groups = hashMapOf<String, String>()

        val cursor = obtainCursor(
            arrayOf(
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
            ),
            arrayOf(
                ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE,
                contactId
            )
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val groupId = getField(
                    cursor,
                    ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
                )
                val group = ALL_GROUPS[groupId]
                if (groupId != null && group != null) {
                    groups[groupId] = group
                }
            }
        }

        cursor?.close()
        return groups
    }

    private val ALL_GROUPS = getAllGroups()

    private fun getAllGroups(): HashMap<String, String> {
        val groups: HashMap<String, String> = hashMapOf()

        val cursor = contentResolver.query(
            ContactsContract.Groups.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor?.count ?: 0 > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val id = getField(cursor, ContactsContract.Groups._ID)
                val name = getField(cursor, ContactsContract.Groups.TITLE)
                if (id != null && name != null) {
                    groups[id] = name
                }
            }
        }

        cursor?.close()
        return groups
    }
}