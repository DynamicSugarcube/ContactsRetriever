package com.example.contactsretriever.contactinfo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.Contact
import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.fragment_contact_info.*
import kotlinx.android.synthetic.main.fragment_contact_info.view.*

class ContactInfoFragment : Fragment() {

    private val TAG = "ContactInfo"

    private val KEY_CONTACT = "contact"

    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var photoImage: ImageView
    private lateinit var playRingtoneButton: Button

    private lateinit var phonesRecyclerView: RecyclerView
    private lateinit var phonesAdapter: PhonesAdapter
    private lateinit var phonesViewManager: RecyclerView.LayoutManager

    private lateinit var emailsRecyclerView: RecyclerView
    private lateinit var emailsAdapter: EmailsAdapter
    private lateinit var emailsViewManager: RecyclerView.LayoutManager

    private lateinit var groupsRecyclerView: RecyclerView
    private lateinit var groupsAdapter: GroupsAdapter
    private lateinit var groupsViewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: ContactInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contact_info, container, false)

        firstName = view.contact_first_name
        lastName = view.contact_last_name
        photoImage = view.contact_photo
        playRingtoneButton = view.play_ringtone_button

        val contact = arguments?.getParcelable(KEY_CONTACT) as? Contact
        if (contact != null) {
            Log.d(TAG, "Provided contact: $contact")
            val viewModelFactory = ContactInfoViewModelFactory(contact)
            viewModel = ViewModelProvider(this, viewModelFactory)
                .get(ContactInfoViewModel::class.java)

            firstName.text = contact.firstName
            lastName.text = contact.lastName

            if (contact.photoUri != "") {
                photoImage.setImageURI(
                    Uri.parse(contact.photoUri)
                )
            } else {
                photoImage.setImageResource(R.drawable.ic_launcher_background)
            }

            val ringtone = context?.let {
                contact.getRingtone(it)
            }
            playRingtoneButton.setOnClickListener {
                ringtone?.play()
            }

        } else {
            Log.e(TAG, "Provided contact is null!")
        }

        phonesViewManager = LinearLayoutManager(this.context)
        phonesAdapter = PhonesAdapter(viewModel.contactPhones)
        phonesRecyclerView = view.phones_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = phonesViewManager
            adapter = phonesAdapter
        }

        emailsViewManager = LinearLayoutManager(this.context)
        emailsAdapter = EmailsAdapter(viewModel.contactEmails)
        emailsRecyclerView = view.emails_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = emailsViewManager
            adapter = emailsAdapter
        }

        groupsViewManager = LinearLayoutManager(this.context)
        groupsAdapter = GroupsAdapter(viewModel.contactGroups)
        groupsRecyclerView = view.groups_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = groupsViewManager
            adapter = groupsAdapter
        }

        return view
    }
}

