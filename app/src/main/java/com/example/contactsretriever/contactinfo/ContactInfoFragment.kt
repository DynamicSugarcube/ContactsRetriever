package com.example.contactsretriever.contactinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.R

class ContactInfoFragment : Fragment() {

    private lateinit var phonesRecyclerView: RecyclerView
    private lateinit var phonesAdapter: PhonesAdapter

    private lateinit var emailsRecyclerView: RecyclerView
    private lateinit var emailsAdapter: EmailsAdapter

    private lateinit var groupsRecyclerView: RecyclerView
    private lateinit var groupsAdapter: GroupsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contact_info, container, false)



        return view
    }
}

