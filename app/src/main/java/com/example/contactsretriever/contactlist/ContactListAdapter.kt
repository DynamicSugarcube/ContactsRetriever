package com.example.contactsretriever.contactlist

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.Contact
import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(private val viewModel: ContactListViewModel) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    private val TAG = "ContactList[Adapter]"

    var data = listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(
        itemView: View,
        private val viewModel: ContactListViewModel
    ) : RecyclerView.ViewHolder(itemView) {

        private val contactFirstName = itemView.contact_first_name
        private val contactLastName = itemView.contact_last_name
        private val contactPhoto = itemView.contact_photo
        private val contactRingtone = itemView.contact_ringtone

        fun bind(contact: Contact) {
            contactFirstName.text = contact.firstName
            contactLastName.text = contact.lastName

            if (contact.photoUri != "") {
                contactPhoto.setImageURI(
                    Uri.parse(contact.photoUri)
                )
            } else {
                contactPhoto.setImageResource(R.drawable.ic_launcher_background)
            }

            val ringtone = contact.getRingtone(viewModel.context)
            contactRingtone.setOnClickListener {
                ringtone.play()
            }

            itemView.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_contactListFragment_to_contactInfoFragment
                )
            }
        }

        companion object {
            fun from(parent: ViewGroup, viewModel: ContactListViewModel) : ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_list_item, parent, false)
                return ViewHolder(view, viewModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "Data size is ${data.size}")
        return data.size
    }
}