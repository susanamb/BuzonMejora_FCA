package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*



class Login : AppCompatActivity() {
    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //BOTON PARA REGRESAR
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }//FIN BOTON REGRESO

        //BOTON PARA ENTRAR A SEGUIMIENTO
        entrar.setOnClickListener {
            val intent = Intent(this, MenuAdmin::class.java)
            startActivity(intent)
            val progressbar = BtnLoadingProgressbar(it) // icono de carga en el boton entrar

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

