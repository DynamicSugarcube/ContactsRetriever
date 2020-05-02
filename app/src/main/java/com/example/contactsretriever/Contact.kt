package com.example.contactsretriever

import android.content.Context
import android.graphics.Bitmap
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.provider.MediaStore

data class Contact(
    val id: Int,
    val name: String,
    val photoUri: String,
    val ringerUri: String,
    val phones: MutableList<Phone> = mutableListOf(),
    val emails: MutableList<String> = mutableListOf(),
    val groups: HashMap<String, String> = hashMapOf()
) {

    val firstName = name.substringBeforeLast(" ")
    val lastName = if (name.contains(" ")) {
        name.substringAfterLast(" ")
    } else {
        ""
    }

    data class Phone(
        val number: String,
        val type: Type
    ) {
        enum class Type {
            WORK,
            MOBILE,
            OTHER
        }
    }

    fun getRingtone(context: Context): Ringtone = RingtoneManager.getRingtone(
        context,
        Uri.parse(ringerUri)
    )
}