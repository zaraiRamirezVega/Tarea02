package com.example.contactos

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ContactFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val numberInput = findViewById<TextInputEditText>(R.id.numberInput)
        val addButton = findViewById<MaterialButton>(R.id.addButton)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        addButton.setOnClickListener {
            // Handle the add button click here
            finish()
        }
    }
}