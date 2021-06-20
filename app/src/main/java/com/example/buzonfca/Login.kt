package com.example.buzonfca

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.resetpass_layout.*
import kotlinx.android.synthetic.main.resetpass_layout.view.*


class Login : AppCompatActivity() {
    val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //BOTON PARA REGRESAR
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }//FIN BOTON REGRESO

        //BOTON PARA ENTRAR A SEGUIMIENTO
        entrar.setOnClickListener {
            val intent = Intent(this, MenuAdmin::class.java)
            startActivity(intent)
            /*val progressbar = BtnLoadingProgressbar(it) // icono de carga en el boton entrar

            if(userInput.text!!.isNotEmpty()  && passInput.text!!.isNotEmpty() ){ //valida que los campos no esten vacios
                progressbar.setLoading() //muestra icono de carga
                handler.postDelayed({

                FirebaseAuth.getInstance() //Valida usuario desde auth de Firebase
                    .signInWithEmailAndPassword(userInput.text.toString(), passInput.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful) { //si se encuentra el usuario y los datos son correctos
                            progressbar.setState(true) { // reemplaza el icono de carga por el de acceso
                                 //MANDA A LA PANTALLA DE MENU PARA SEGUIMIENTO
                                val intent = Intent(this, MenuAdmin::class.java)
                                startActivity(intent)
                            }
                        } else { //si los datos son incorrectos
                            startError(progressbar) // se reemplaza el icono de carga por el de error
                            // muestra error
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("Ocurrio un error, intentalo de nuevo")
                            builder.setPositiveButton("Aceptar", null)
                            val dialog: AlertDialog = builder.create()
                            dialog.show() //fin muestra error
                        }
                    }

                        //},1500)

                },900)
            }
            else{ //si el usuario no llena los campos necesarios
                Toast.makeText(this,"Llena los campos", Toast.LENGTH_SHORT).show()
            }*/

        }

        resetpass.setOnClickListener {

            val view = View.inflate(this@Login,R.layout.resetpass_layout,null)
            val mBuilder = AlertDialog.Builder(this@Login).setView(view)

            val dialog = mBuilder.create()
            dialog.show()

            view.send_btn.setOnClickListener {
                dialog.dismiss()
                val correo = view.correoReset.text!!.toString()

                FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
                    .addOnCompleteListener{it ->
                        if(it.isSuccessful){
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Listo")
                            builder.setMessage("Revisa tu bandeja de entrada")
                            builder.setPositiveButton("Aceptar", null)
                            val dialog: AlertDialog = builder.create()
                            dialog.show()

                        }else{
                            var err = it.exception!!.message.toString()
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("$err, intentalo de nuevo")
                            builder.setPositiveButton("Aceptar", null)


                            val dialog: AlertDialog = builder.create()
                            dialog.show()


                        }
                    }
            }


        }
    }
    //REGRESAR A LA PANTALLA ANTERIOR
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    //FUNCION PARA EL ICONO DE CARGA EN CASO DE ERROR
    private fun startError(progressbar: BtnLoadingProgressbar) {
        progressbar.reset()
        handler.postDelayed({
            progressbar.setLoading()
            handler.postDelayed({
                progressbar.setState(false){ // executed after animation end
                    handler.postDelayed({
                        progressbar.reset()
                    },1000)
                }
            },200)
        },200)
    }
    }

