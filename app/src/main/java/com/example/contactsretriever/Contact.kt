package com.example.contactsretriever

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

data class Contact(
    val id: String,
    val name: String,
    val photoUri: String,
    val ringerUri: String
) {

    val firstName = name.substringBeforeLast(" ")
    val lastName = if (name.contains(" ")) name.substringAfterLast(" ") else ""

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