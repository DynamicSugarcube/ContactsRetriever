package com.example.contactsretriever.contactlist

import com.example.contactsretriever.Contact

object ContactsRepo {

    val contacts = mutableListOf<Contact>()

    /**
     * The following hash maps contain info about every contact
     * Appropriate info can be retrieved by providing contact ID
     */
    val phonesHashMap = hashMapOf<String, MutableList<Contact.Phone>>()
    val emailsHashMap = hashMapOf<String, MutableList<String>>()
    // Associated list here contains group IDs
    val groupsMembershipHashMap = hashMapOf<String, MutableList<String>>()
}