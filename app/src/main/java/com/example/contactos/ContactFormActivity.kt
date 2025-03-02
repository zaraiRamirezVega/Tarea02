package com.example.contactos

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.contactos.data.ContactDbHelper

class ContactFormActivity : AppCompatActivity() {
    private lateinit var dbHelper: ContactDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        dbHelper = ContactDbHelper(this)

        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val numberInput = findViewById<TextInputEditText>(R.id.numberInput)
        val addButton = findViewById<MaterialButton>(R.id.addButton)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        addButton.setOnClickListener {
            val name = nameInput.text?.toString()?.trim()
            val number = numberInput.text?.toString()?.trim()

            if (name.isNullOrEmpty()) {
                nameInput.error = "Porfavor entra el nombre"
                return@setOnClickListener
            }

            if (number.isNullOrEmpty() || number.length != 9) {
                numberInput.error = "El numero de telefono debe tener 9 digitos"
                return@setOnClickListener
            }

            val id = dbHelper.addContact(name, number)
            if (id != -1L) {
                Toast.makeText(this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error guardando el contacto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}