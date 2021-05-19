package com.example.buzonfca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val folio = folio.text.toString()
            if( folio.isNotEmpty() ){
                database = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
                database.child(folio).get().addOnSuccessListener {
                    if(it.exists()){
                        val stat = it.child("Status").value
                        textView9.text = stat.toString()

                        Toast.makeText(this,"Consulta exitosa ",Toast.LENGTH_SHORT).show()

                    }else{
                        textView9.text = " "
                        Toast.makeText(this,"El folio ingresado no existe",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Error 404", Toast.LENGTH_SHORT).show()
                }

            }else{
                textView9.text = " "
                Toast.makeText(this,"Ingrese el folio a consultar",Toast.LENGTH_SHORT).show()
            }

        }

    }

}


