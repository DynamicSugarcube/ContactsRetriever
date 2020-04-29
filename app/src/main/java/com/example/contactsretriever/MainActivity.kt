package com.example.contactsretriever

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.contactsretriever.retrievers.ContactsRetriever


class MainActivity : AppCompatActivity() {

    private val TAG = "ContactsRetriever"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            1
        )

        val contactsRetriever =
            ContactsRetriever(
                applicationContext
            )
        val contacts = contactsRetriever.retrieve()
        Log.i(TAG, "List of contacts:\n$contacts")
    }
}
