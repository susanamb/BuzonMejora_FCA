package com.example.buzonfca

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_selected_q_s.*

class SelectedQS : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Quejas y Sugerencias")
        var folio : String
        //textView16.setMovementMethod(ScrollingMovementMethod())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_q_s)

        database = Firebase.database.reference
        var com = intent.getStringExtra("comentario")

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }

           database.child("Quejas y Sugerencias").orderByChild("Comentario").equalTo("$com").limitToFirst(1).addValueEventListener(object: ValueEventListener{

               override fun onDataChange(snapshot: DataSnapshot) {
                   if(snapshot.exists()){
                       var qs = snapshot.value.toString()

                       folio = qs.substringBefore('=').dropWhile { !it.isLetterOrDigit() }


                       database.child("Quejas y Sugerencias").child("$folio").get().addOnSuccessListener {
                           if (it.exists()) {
                               val categoria = it.child("Categoria").value
                               val asunto = it.child("Asunto").value
                               val coment = it.child("Comentario").value
                               val status = it.child("Status").value
                               val saobservacion = it.child("SAComentario").value

                               val pos = when (status) {
                                   "Resuelto" -> {
                                       1
                                   }
                                   "Falta informacion" -> {
                                       2
                                   }
                                   else -> {
                                       0
                                   }
                               }

                               spinner5.setSelection(pos)
                               textView5.text = folio
                               textView8.text = categoria.toString()
                               textView13.text = asunto.toString()
                               textView16.text = coment.toString()
                               if(saobservacion != null){
                                   otros.setText(saobservacion.toString())
                               }

                               if(status == "Pendiente, sin leer") {
                                   myRef.child("$folio/Status").setValue("Pendiente, leído")

                               }


                           }else{
                               val builder = AlertDialog.Builder(this@SelectedQS)
                               builder.setTitle("Error")
                               builder.setMessage("Ocurrio un error, intentalo de nuevo")
                               builder.setPositiveButton("Aceptar", null)
                               val dialog: AlertDialog = builder.create()
                               dialog.show() //fin muestra error
                           }
                       }

                       update.setOnClickListener {
                           var seg: Spinner = findViewById(R.id.spinner5)
                           var seguimiento = seg.selectedItem.toString()

                           if(otros.text.isNotEmpty()){
                               var sa = otros.text.toString()
                               myRef.child("$folio/SAComentario").setValue(sa)
                           }else{
                               //Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                           }
                           myRef.child("$folio/Status").setValue(seguimiento)

                           val intent = Intent(this@SelectedQS, DataUpdatedView::class.java)
                           startActivity(intent)
                       }
                   }
               }
               override fun onCancelled(error: DatabaseError) {
                   TODO("Not yet implemented")
               }
           } )



    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MenuAdmin::class.java)
        startActivity(intent)
        return true
    }
}
