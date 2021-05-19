package com.example.buzonfca

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD:app/src/main/java/com/example/buzonfca/Form1.kt
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_form1.*
import android.text.method.ScrollingMovementMethod

import android.widget.TextView

class Form1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    //lateinit var matricula: tex
    //private lateinit var getdb : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        var con = ""
        var i = true
        var asunt =""


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form1)




        //scroll del texto
       val desc = findViewById<View>(R.id.editTextTextMultiLine) as TextView
        desc.setMovementMethod(ScrollingMovementMethod());

        //BOTON PARA REGRESAR
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //BOTON DE ENVIAR

        enviar.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (i) {
                        con = dataSnapshot.childrenCount.toString()
                        when (con.length) {
                            1 -> {
                                con = "000$con"
                            }
                            2 -> {
                                con = "00$con"
                            }
                            3 -> {
                                con = "0$con"
                            }
                        }
                        var cat: Spinner = findViewById(R.id.spinner)
                        var cate = cat.selectedItem.toString()
                        var asu: Spinner = findViewById(R.id.spinner2)
                        asunt = if(asu.selectedItemPosition == (asu.count)-1){
                            otro.text.toString()
                        }else {
                            asu.selectedItem.toString()
                        }
                        var mat = editTextTextMultiLine.text.toString()
                        var correo = correo.text.toString()

                        myRef.child("$con/Categoria").setValue(cate)
                        myRef.child("$con/Asunto").setValue(asunt)
                        myRef.child("$con/Comentario").setValue(mat)
                        if (correo != null) {
                            myRef.child("$con/Correo").setValue(correo)
                        }
                        myRef.child("$con/Status").setValue("Pendiente, sin leer")
                        i = false
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

            val intent = Intent(this, FolioView::class.java)
            intent.putExtra("Folio"," $con")
            startActivity(intent)
        }
        
        val cat: Spinner = findViewById(R.id.spinner)
        cat.onItemSelectedListener = this
    
        val asunto: Spinner = findViewById(R.id.spinner2)
        asunto.onItemSelectedListener = this


    }
    //RADIO BUTTONS
   //BACK BUTTON
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    //SPINNERS
    override fun onNothingSelected(parent: AdapterView<*>?) {
        textInputLayout3.visibility = View.INVISIBLE
        otro.visibility = View.INVISIBLE
    }

   //SPINNERS ACTION
    override fun onItemSelected(parent: AdapterView<*>?, vositiew: View?, pion: Int, id: Long) {
        if (parent != null) {
               var pos = parent.selectedItemPosition
               if(pos == (parent.count)-1){
                   otro.visibility = View.VISIBLE

               }else{
                  otro.visibility = View.INVISIBLE

               }
           }

        }

    }
