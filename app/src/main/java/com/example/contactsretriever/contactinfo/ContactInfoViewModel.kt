package com.example.contactsretriever.contactinfo

import androidx.lifecycle.ViewModel
import com.example.contactsretriever.Contact
import com.example.contactsretriever.contactlist.ContactsRepo

class ContactInfoViewModel(val contact: Contact) : ViewModel() {

    val contactPhones: List<Contact.Phone>
            = ContactsRepo.phonesHashMap[contact.id] ?: listOf()

    val contactEmails: List<String>
            = ContactsRepo.emailsHashMap[contact.id] ?: listOf()

    val contactGroups: List<String>
            = ContactsRepo.groupsMembershipHashMap[contact.id] ?: listOf()
}