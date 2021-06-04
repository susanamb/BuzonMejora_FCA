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


//CONSULTAS EN LA BASE DE DATOS DEL FOLIO DE LA QUEJA/SUGERENCIA
class ActivityMenu1 : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu1)


        database = Firebase.database.reference

        //BOTON DE REGRESO
        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }//FIN BOTON DE REGRESO

        //ACCION DE BOTON DE CONSULTAR
        search.setOnClickListener {
            it.hideKeyboard() //ocultar teclado
            val folio = folio.text.toString()//recupera folio ingresado
            if( folio.isNotEmpty() ){ //valida que no sea valor nulo
                database = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")//busca en la base de datos
                database.child(folio).get().addOnSuccessListener {
                    if(it.exists()){ // si encontro el folio guarda los datos
                        var stat = it.child("Status").value //valor del status del folio
                        if(it.child("SAComentario").exists()){ // si hay observaciones de sa lo guarda
                            val sacom = it.child("SAComentario").value
                            stat = "$stat, \n $sacom" //concatena status y la observacion de sa
                        }

                        textView20.text = stat.toString() //muestra en textview el status y la oservacion

                        Toast.makeText(this,"Consulta exitosa ",Toast.LENGTH_SHORT).show()

                    }else{
                        textView20.text = " "//si no hay informacion , no muestra nada
                        Toast.makeText(this,"El folio ingresado no existe",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

            }else{
                //si el usuario no ingresa ningun valor, no muestra nada
                textView9.text = " "
                Toast.makeText(this,"Ingrese el folio a consultar",Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun View.hideKeyboard() { //funcion para ocultar el teclado
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}


