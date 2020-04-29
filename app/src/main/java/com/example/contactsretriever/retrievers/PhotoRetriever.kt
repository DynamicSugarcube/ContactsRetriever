package com.example.contactsretriever.retrievers

import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract

object PhotoRetriever {

    fun retrievePhotoUri(contactId: String): String {
        val person = ContentUris.withAppendedId(
            ContactsContract.Contacts.CONTENT_URI,
            contactId.toLong()
        )
        val photoUri = Uri.withAppendedPath(
            person,
            ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
        )
        return photoUri.toString()
    }
}