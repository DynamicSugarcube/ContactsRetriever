package com.example.contactsretriever.contactinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.emails_list_item.view.*

class EmailsAdapter(private val data: List<String>) :
    RecyclerView.Adapter<EmailsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val emailAddress = itemView.email_address

        fun bind(email: String) {
            emailAddress.text = email
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder = ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.emails_list_item, parent, false
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