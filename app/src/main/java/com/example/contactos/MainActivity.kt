package com.example.contactos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactos.data.Contact
import com.example.contactos.data.ContactDbHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: ContactDbHelper
    private lateinit var adapter: ContactAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: View
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        dbHelper = ContactDbHelper(this)
        
        recyclerView = findViewById(R.id.contactsRecyclerView)
        emptyView = findViewById(R.id.emptyView)
        fab = findViewById(R.id.fab)
        
        adapter = ContactAdapter(mutableListOf(), dbHelper, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val swipeHandler = SwipeToDeleteCallback(adapter, this)
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up FAB click listener
        fab.setOnClickListener {
            navigateToAddContact()
        }

        // Set up empty view button click listener
        emptyView.findViewById<MaterialButton>(R.id.addContactButton).setOnClickListener {
            navigateToAddContact()
        }
    }

    private fun navigateToAddContact() {
        val intent = Intent(this, ContactFormActivity::class.java)
        startActivity(intent)
    }

    private fun updateEmptyState(contacts: List<Contact>) {
        if (contacts.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            fab.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            fab.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val contacts = dbHelper.getAllContacts()
        adapter.updateContacts(contacts)
        updateEmptyState(contacts)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}