package com.example.contactsretriever.contactlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.fragment_contact_list.view.*

class ContactListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ContactListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: ContactListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ContactListViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ContactListViewModel::class.java)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ContactListAdapter(viewModel)

        recyclerView = view.contact_list_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            viewAdapter.data = it
        })

        return view
    }

}
