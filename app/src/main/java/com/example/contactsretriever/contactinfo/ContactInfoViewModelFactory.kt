package com.example.contactsretriever.contactinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsretriever.Contact

class ContactInfoViewModelFactory(private val contact: Contact) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactInfoViewModel::class.java)) {
            return ContactInfoViewModel(contact) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}