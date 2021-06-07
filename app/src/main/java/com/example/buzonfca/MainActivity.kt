package com.example.buzonfca

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        var contador =0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val iniciarbtn = findViewById<iniciarbtn>(R.id.Button)

        //BOTON PARA PANTALLA DE REALIZAR UNA NUEVA QUEJA O SUGERENCIA
        iniciarbtn.setOnClickListener{
            val intent = Intent(this, Form1::class.java)
            startActivity(intent)
        }

        //BOTON PARA PANTALLA DE CONSULTA DE FOLIO
        consul.setOnClickListener{
            val intents = Intent(this, ActivityMenu1::class.java)
            startActivity(intents)
        }

        //BOTON PARA PANTALLA DE INICIO DE SESION DE S.A
        log.setOnClickListener {
            val intent2 = Intent(this, Login::class.java)
            startActivity(intent2)
        }

        //CUENTA LA CANTIDAD DE CASOS CON EL ESTATUS RESUELTO
        val query: Query = myRef.orderByChild("Status").equalTo("Resuelto")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (issue in dataSnapshot.children) {
                        contador = contador + 1
                    }
                }
                textView17.text = contador.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


    }
        override fun onBackPressed() {
            finish()
        }
    }