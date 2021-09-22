 package com.example.buzonfca

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_form1.*
import java.text.SimpleDateFormat
import java.util.regex.Pattern


class Form1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        var con = " "

        var i = true
        var calendar: Calendar
        var simpleDateFormat: SimpleDateFormat



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form1)


        //scroll del texto
        val desc = findViewById<View>(R.id.editTextTextMultiLine) as TextView
        desc.movementMethod = ScrollingMovementMethod();

        //BOTON PARA REGRESAR
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        //BOTON DE ENVIAR

        enviar.setOnClickListener {

            if(editTextTextMultiLine.text.isNotEmpty()) { //valida que el comentario no se nulo
                if(asunto() && otro.text.isNotEmpty() || !asunto() && otro.text.isEmpty()) {
                    val mail = correo.text.toString()
                    val uabcmail = validarEmail("$mail")
                    if (mail.isEmpty() || mail.isNotEmpty() && uabcmail) { //valida que el campo de correo no sea nulo y sea un correo valido
                        myRef.addValueEventListener(object : ValueEventListener {

                            @RequiresApi(Build.VERSION_CODES.N)
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (i) {

                                    var cat: Spinner = findViewById(R.id.spinner)
                                    var cate = cat.selectedItem.toString() //recupera la categoria seleccionada; queja o sugerencia
                                    val ic = cate.take(1)//toma la primera letra de la categoria
                                    var asu: Spinner = findViewById(R.id.spinner2)
                                    var asunt = if (asu.selectedItemPosition == (asu.count) - 1) { //valida si se selecciono un asinto especiffico u otro
                                        otro.text.toString() //se guarda el dato de otro en asunto
                                    } else {
                                        asu.selectedItem.toString() // se guarda el valor seleccionado del spinner de asunto
                                    }
                                    val ia = asunt.take(2) //toma las primeras dos letras del asunto
                                    var mat = editTextTextMultiLine.text.toString() //valor del comentario qs
                                    calendar = Calendar.getInstance() //recupera la fecha actual
                                    simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")//formato de la fecha
                                    var fecha = simpleDateFormat.format(calendar.time).toString() //guarda la fecha como string

                                    con = (dataSnapshot.childrenCount + 1).toString() //numero de registros en bd +1 para iniciar el conteo en 1

                                    //FIN GUARDAR DATOS
                                    when { //genera el folio con el formato requerido, juntanto digitos con las iniciales de categoria y asunto
                                        con.toInt() < 10 -> {
                                            con = "0${ic}0${ia}0$con"
                                        }
                                        con.toInt() in 10..99 -> {
                                            con = "0${ic}0${ia}$con"
                                        }
                                        con.toInt() in 100..999 -> {
                                            val mas = con.take(1)
                                            con = con.takeLast(2)
                                            con = "0${ic}$mas${ia}$con"
                                        }


                                        //GUARDA LOS DATOS EN FIREBASE
                                    }
                                    val letra = getRandomString()
                                    //Log.d("Hello", " letra -> $letra")

                                    con = ("$letra$con").toUpperCase() //agrega letra aleatoria y lo convierte en mayusculas
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

                                    val intent = Intent(this@Form1, FolioView::class.java)
                                    intent.putExtra("Folio", "$con")//envia folio generado a la otra vista
                                    startActivity(intent)

                                }

                                //ENVIA AL USUARIO A LA PANTALLA DONDE MUESTRA EL FOLIO OBTENIDO

                            }

                            override fun onCancelled(databaseError: DatabaseError) {

                            }


                        })


                    } else { //si se introdujo correo pero no es valido
                        Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }else{//si el usuario no introdujo ningun comentario
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
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

                otro.visibility = View.GONE // si elige uno predeterminado, oculta el campo

            }
        }

    }

    fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..1)
            .map { allowedChars.random() }
            .joinToString("")
    }
    private fun asunto() :Boolean{
        var asu: Spinner = findViewById(R.id.spinner2)
        return asu.selectedItemPosition == (asu.count) - 1
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
}

