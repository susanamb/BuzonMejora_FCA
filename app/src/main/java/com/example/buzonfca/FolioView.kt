package com.example.buzonfca

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_folio_view.*
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class FolioView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Quejas y Sugerencias")
        var cont =0

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folio_view)




        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (issue in dataSnapshot.children) {
                        cont += 1
                    }
                }
               // cont += 1
                var folio = cont.toString()
                when (folio.length) { //anteponer los 0 para el folio
                    1 -> {
                        folio = " 000$folio"
                    }
                    2 -> {
                        folio = " 00$folio"
                    }
                    3 -> {
                        folio = " 0$folio"
                    }
                }
                textView11.text = folio
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })




        //boton de aceptar regresa a la pantalla de inicio
        submit.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("EditText", textView11.getText().toString())
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(this, "Folio copiado", Toast.LENGTH_SHORT).show()



            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}