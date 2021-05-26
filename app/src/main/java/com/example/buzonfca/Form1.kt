package com.example.buzonfca

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
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
import java.text.SimpleDateFormat

import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi


class Form1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        var con = " "
        var i = true

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form1)

        var calendar: Calendar
        var simpleDateFormat: SimpleDateFormat

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
            if(editTextTextMultiLine.text.isNotEmpty()) {
                myRef.addValueEventListener(object : ValueEventListener {

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (i) {
                            con = (dataSnapshot.childrenCount + 1).toString()
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
                            var asunt = if (asu.selectedItemPosition == (asu.count) - 1) {
                                otro.text.toString()
                            } else {
                                asu.selectedItem.toString()
                            }
                            var mat = editTextTextMultiLine.text.toString()
                            calendar = Calendar.getInstance()
                            simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                            var fecha = simpleDateFormat.format(calendar.time).toString()
                            //var fecha = LocalDate.now()

                            myRef.child("$con/Categoria").setValue(cate)
                            myRef.child("$con/Asunto").setValue(asunt)
                            myRef.child("$con/Comentario").setValue(mat)
                            if (correo.text.isNotEmpty()) {
                                var mail = correo.text.toString()
                                myRef.child("$con/Correo").setValue(mail)
                            }
                            myRef.child("$con/Status").setValue("Pendiente, sin leer")
                            myRef.child("$con/Fecha").setValue(fecha)

                            i = false
                        }
                        val intent = Intent(this@Form1, FolioView::class.java)
                        intent.putExtra("Folio", " $con")
                        startActivity(intent)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }

                })
            }else{
                Toast.makeText(this, "Escribe tu comentario", Toast.LENGTH_SHORT).show()
            }

        }

        val cat: Spinner = findViewById(R.id.spinner)
        cat.onItemSelectedListener = this

        val asunto: Spinner = findViewById(R.id.spinner2)
        asunto.onItemSelectedListener = this


    }

    //BACK BUTTON
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    //SPINNERS
    override fun onNothingSelected(parent: AdapterView<*>?) {
        otro.visibility = View.INVISIBLE
    }

    //SPINNERS ACTION
    override fun onItemSelected(parent: AdapterView<*>?, vositiew: View?, pion: Int, id: Long) {
        if (parent != null) {
            var pos = parent.selectedItemPosition
            if(pos == 4 ){
                otro.visibility = View.VISIBLE

            }else{
                otro.visibility = View.INVISIBLE

            }
        }

    }

}