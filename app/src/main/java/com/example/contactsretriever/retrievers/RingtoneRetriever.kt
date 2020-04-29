package com.example.contactsretriever.retrievers

import android.media.RingtoneManager

object RingtoneRetriever {

    fun retrieveRingtoneUri(uri: String?) =
        uri ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString()
}