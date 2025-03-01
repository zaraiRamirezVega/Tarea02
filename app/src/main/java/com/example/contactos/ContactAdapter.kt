package com.example.contactos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.contactos.data.Contact
import com.example.contactos.data.ContactDbHelper
import com.google.android.material.textview.MaterialTextView

class ContactAdapter(
    private var contacts: MutableList<Contact>,
    private val dbHelper: ContactDbHelper,
    private val context: android.content.Context  // Add context parameter
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarText: MaterialTextView = view.findViewById(R.id.avatarText)
        val nameText: MaterialTextView = view.findViewById(R.id.nameText)
        val numberText: MaterialTextView = view.findViewById(R.id.numberText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.avatarText.text = contact.name.firstOrNull()?.uppercase() ?: "?"
        holder.nameText.text = contact.name
        holder.numberText.text = contact.number
    }

    override fun getItemCount() = contacts.size

    fun deleteContact(position: Int) {
        val contact = contacts[position]
        if (dbHelper.deleteContact(contact.id)) {
            contacts.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(
                context,  // Use the context parameter instead of dbHelper.context
                "${contact.name} was successfully deleted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts.toMutableList()
        notifyDataSetChanged()
    }
}