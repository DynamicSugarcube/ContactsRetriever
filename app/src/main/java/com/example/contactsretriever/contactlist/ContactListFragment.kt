package com.example.contactsretriever.contactlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsretriever.Contact

import com.example.contactsretriever.R
import kotlinx.android.synthetic.main.fragment_contact_list.view.*

class ContactListFragment : Fragment() {

    private val CONTACT_LIST = listOf(
        Contact(0, "NAME_0 SURNAME_0", "", ""),
        Contact(1, "NAME_1 SURNAME_1", "", ""),
        Contact(2, "NAME_2 SURNAME_2", "", ""),
        Contact(3, "NAME_3 SURNAME_3", "", ""),
        Contact(4, "NAME_4 SURNAME_4", "", ""),
        Contact(5, "NAME_5 SURNAME_5", "", ""),
        Contact(6, "NAME_6 SURNAME_6", "", ""),
        Contact(7, "NAME_7 SURNAME_7", "", ""),
        Contact(8, "NAME_8 SURNAME_8", "", ""),
        Contact(9, "NAME_9 SURNAME_9", "", "")
    )

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
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
        viewAdapter = ContactListAdapter(viewModel.fetchContacts(), viewModel)

        recyclerView = view.contact_list_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

}
