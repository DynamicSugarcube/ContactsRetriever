package com.example.contactsretriever

data class Contact(
    val id: Int,

    // TODO("split it into first and last names")
    val name: String,
    val phones: MutableList<Phone> = mutableListOf(),
    val emails: MutableList<String> = mutableListOf()
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
}