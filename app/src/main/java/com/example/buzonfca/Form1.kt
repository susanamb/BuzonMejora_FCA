package com.example.buzonfca

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import java.text.SimpleDateFormat
import java.util.regex.Pattern



class Form1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        var con = " "
        var i = true




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form1)

        //variables para generar la fecha
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
            if(editTextTextMultiLine.text.isNotEmpty()) { //valida que el comentario no se nulo
                val mail = correo.text.toString()
                val uabcmail = validarEmail("$mail")
                if(mail.isEmpty() || mail.isNotEmpty() && uabcmail){ //valida que el campo de correo no sea nulo y sea un correo valido
                    myRef.addValueEventListener(object : ValueEventListener {

                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (i) {
                                con = (dataSnapshot.childrenCount + 1).toString() //numero de registros en bd +1 para iniciar el conteo en 1
                                when (con.length) { //anteponer los 0 para el folio
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
                                var cate = cat.selectedItem.toString() //recupera la categoria seleccionada; queja o sugerencia
                                var asu: Spinner = findViewById(R.id.spinner2)
                                var asunt = if (asu.selectedItemPosition == (asu.count) - 1) { //valida si se selecciono un asinto especiffico u otro
                                    otro.text.toString() //se guarda el dato de otro en asunto
                                } else {
                                    asu.selectedItem.toString() // se guarda el valor seleccionado del spinner de asunto
                                }
                                var mat = editTextTextMultiLine.text.toString() //valor del comentario qs
                                calendar = Calendar.getInstance() //recupera la fecha actual
                                simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")//formato de la fecha
                                var fecha = simpleDateFormat.format(calendar.time).toString() //guarda la fecha como string

                                //GUARDA LOS DATOS EN FIREBASE
                                myRef.child("$con/Categoria").setValue(cate)
                                myRef.child("$con/Asunto").setValue(asunt)
                                myRef.child("$con/Comentario").setValue(mat)
                                if (correo.text.isNotEmpty()) { //si hay un correo lo guarda
                                    var mail = correo.text.toString()
                                    myRef.child("$con/Correo").setValue(mail)
                                }
                                myRef.child("$con/Status").setValue("Pendiente, sin leer")
                                myRef.child("$con/Fecha").setValue(fecha)
                                //FIN GUARDAR DATOS

                                i = false
                            }
                            //ENVIA AL USUARIO A LA PANTALLA DONDE MUESTRA EL FOLIO OBTENIDO

                            val intent = Intent(this@Form1, FolioView::class.java)
                            intent.putExtra("Folio", " $con")//envia folio generado a la otra vista
                            startActivity(intent)
                           // mostrarDialogoPersonalizado()

                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }

                    })
                }else{ //si se introdujo correo pero no es valido
                    Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show()
                }

            }else{//si el usuario no introdujo ningun comentario
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
    //SPINNERS CUANDO NO HAY NADA SELECCIONADO
    override fun onNothingSelected(parent: AdapterView<*>?) {
        otro.visibility = View.INVISIBLE
    }

    //SPINNERS ACTION
    override fun onItemSelected(parent: AdapterView<*>?, vositiew: View?, pion: Int, id: Long) {
        if (parent != null) {
            var pos = parent.selectedItemPosition
            if(pos == 4 ){ //si se selecciona la opcion de otro
                otro.visibility = View.VISIBLE //muestra el campo para introducir el asunto

            }else{
                otro.visibility = View.INVISIBLE // si elige uno predeterminado, oculta el campo

            }
        }

    }

    //FUNCION PARA VALIDAR CORREO
    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return if(pattern.matcher(email).matches()){ //valida que sea un correo
            val parts: Array<String> = email.split('@').toTypedArray() //separa correo para recoger el dominio
            val dominio = parts[1] // guarda el valor del dominio
            dominio == "uabc.edu.mx" //devuelve true si el correo es de uabc
        }else{
            false
        }
    }

//    private fun mostrarDialogoPersonalizado() {
//        val builder = AlertDialog.Builder(this@Form1)
//        val inflater = layoutInflater
//        val view = inflater.inflate(R.layout.dialog_personalizado, null)
//        builder.setView(view)
//        val dialog = builder.create()
//        dialog.show()
//
//
//        val txt = view.findViewById<TextView>(R.id.text_dialog)
//        txt.text = "Tu comentario se envio correctamente"
//
//        val txt2 = view.findViewById<TextView>(R.id.text_dialog2)
//        txt2.text = "Folio:"
//
//
//        val txt3 = view.findViewById<TextView>(R.id.text_dialog3)
//        val folio = intent.getStringExtra("Folio")
//        txt3.text = folio
//
//        val btnReintentar = view.findViewById<Button>(R.id.button4)
//        btnReintentar.setOnClickListener {
//           // Toast.makeText(applicationContext, "Conectando...", Toast.LENGTH_SHORT)
//            dialog.dismiss()
//        }
//    }

}

