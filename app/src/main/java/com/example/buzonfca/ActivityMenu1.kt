package com.example.buzonfca

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_menu1.*



class ActivityMenu1 : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu1)


        database = Firebase.database.reference

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        search.setOnClickListener {
            it.hideKeyboard()
            val folio = folio.text.toString()
            if( folio.isNotEmpty() ){
                database = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
                database.child(folio).get().addOnSuccessListener {
                    if(it.exists()){
                        var stat = it.child("Status").value
                        if(it.child("SAComentario").exists()){
                            val sacom = it.child("SAComentario").value
                            stat = "$stat, \n $sacom"
                        }

                        textView20.text = stat.toString()

                        Toast.makeText(this,"Consulta exitosa ",Toast.LENGTH_SHORT).show()

                    }else{
                        textView20.text = " "
                        Toast.makeText(this,"El folio ingresado no existe",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

            }else{
                textView9.text = " "
                Toast.makeText(this,"Ingrese el folio a consultar",Toast.LENGTH_SHORT).show()
            }

        }

    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}


