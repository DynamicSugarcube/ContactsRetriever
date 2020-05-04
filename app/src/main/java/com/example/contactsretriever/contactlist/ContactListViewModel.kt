package com.example.contactsretriever.contactlist

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactsretriever.Contact
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application) : ViewModel() {

    private val contentResolver: ContentResolver = application.contentResolver
    val context: Context = application.applicationContext

    private val contactsRetriever = ContactsRetriever(contentResolver)

    val contacts: MutableLiveData<List<Contact>> =
        MutableLiveData(ContactsRepo.contacts)

    fun fetchContacts() {
        GlobalScope.launch {
            contactsRetriever.fetchContacts()
        }
    }
}