package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.activity_menu1.*
import kotlinx.android.synthetic.main.activity_selected_q_s.*

class SelectedQS : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Quejas y Sugerencias")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_q_s)

        database = Firebase.database.reference
        var folio = intent.getStringExtra("folio")

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }

            database = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
        if (folio != null) {
            when (folio.length) {
                1 -> {
                    folio = "000$folio"
                }
                2 -> {
                    folio = "00$folio"
                }
                3 -> {
                    folio= "0$folio"
                }
            }
            database.child(folio).get().addOnSuccessListener {
                if (it.exists()) {
                    val categoria = it.child("Categoria").value
                    val asunto = it.child("Asunto").value
                    val coment = it.child("Comentario").value
                    val status = it.child("Status").value

                    textView5.text = folio
                    textView8.text = categoria.toString()
                    textView13.text = asunto.toString()
                    textView16.text = coment.toString()

                    if(status == "Pendiente, sin leer") {
                        myRef.child("$folio/Status").setValue("Pendiente, leído")
                    }

                    Toast.makeText(this, "Consulta exitosa ", Toast.LENGTH_SHORT).show()

                }
            }
        }

        button.setOnClickListener {
            var seg: Spinner = findViewById(R.id.spinner5)
            var seguimiento = seg.selectedItem.toString()
            if(otros.text.isNotEmpty()){
                var sa = otros.text
                myRef.child("$folio/SAComentario").setValue(sa)

            }
            myRef.child("$folio/Status").setValue(seguimiento)

            val intent = Intent(this, DataView::class.java)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
