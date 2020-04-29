package com.example.contactsretriever

data class Contact(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phones: MutableList<Phone>,
    val emails: MutableList<String>,
    val groups: HashMap<String, String>,
    val ringerUri: String,
    val photoUri: String
) {

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

    fun getRingtone() {
        // TODO not implemented
        // It should return a contact's ringtone by ringerUri
    }

    fun getPhoto() {
        // TODO not implemented
        // It should return a contact's photo by photoUri
    }
}