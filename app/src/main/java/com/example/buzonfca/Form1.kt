package com.example.buzonfca

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD:app/src/main/java/com/example/buzonfca/Form1.kt
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form1.*
=======
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
>>>>>>> 1b2d70806a6f4d7fd08282dd61cafc10361dcd6e:app/src/main/java/com/example/prototipo/Form1.kt

import kotlinx.android.synthetic.main.activity_form1.*

class Form1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {
<<<<<<< HEAD:app/src/main/java/com/example/buzonfca/Form1.kt
    //lateinit var matricula: tex
    //private lateinit var getdb : DatabaseReference
=======

>>>>>>> 1b2d70806a6f4d7fd08282dd61cafc10361dcd6e:app/src/main/java/com/example/prototipo/Form1.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        val con = "0001"

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

            var cat: Spinner = findViewById(R.id.spinner)
            var cate = cat.selectedItem.toString()
            var asu: Spinner = findViewById(R.id.spinner2)
            var asunto = asu.selectedItem.toString()
            var mat = editTextTextMultiLine.text.toString()

            myRef.child(con).child("Categoria").setValue(cate)
            myRef.child("$con/asunto").setValue(asunto)
            myRef.child("$con/Comentario").setValue(mat)

            val intent = Intent(this, FolioView::class.java)
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
        textInputLayout4.visibility = View.INVISIBLE
    }
    //SPINNERS ACTION
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        ///val a = R.id.spinner2
        //textView3.text = a.toString()
        if (parent != null) {
            var pos = parent.selectedItemPosition
            if(pos == (parent.count)-1){
                textInputLayout3.visibility = View.VISIBLE
            }else{
                textInputLayout3.visibility = View.INVISIBLE
            }
        }

    }


}