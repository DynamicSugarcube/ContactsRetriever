package com.example.contactsretriever.contactinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.Contact
import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.phones_list_item.view.*

class PhonesAdapter(private val data: List<Contact.Phone>) :
    RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val phoneNumber = itemView.phone_number
        private val phoneType = itemView.phone_type

        fun bind(phone: Contact.Phone) {
            phoneNumber.text = phone.number
            phoneType.text = phone.type.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder = ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.phones_list_item, parent, false
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}