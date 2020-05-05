package com.example.contactsretriever

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Contact(
    val id: String,
    val name: String,
    val photoUri: String,
    val ringerUri: String
) : Parcelable {

    val firstName = name.substringBeforeLast(" ")
    val lastName = if (name.contains(" ")) name.substringAfterLast(" ") else ""

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(photoUri)
        parcel.writeString(ringerUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}