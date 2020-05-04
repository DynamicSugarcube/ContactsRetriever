package com.example.contactsretriever.contactinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.groups_list_item.view.*

class GroupsAdapter(private val data: List<String>) :
    RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val groupId = itemView.group_id

        fun bind(group: String) {
            groupId.text = group
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder = ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.groups_list_item, parent, false
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