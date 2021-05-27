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

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        entrar.setOnClickListener {
            val progressbar = BtnLoadingProgressbar(it)

            if(userInput.text!!.isNotEmpty()  && passInput.text!!.isNotEmpty() ){
                progressbar.setLoading()
                handler.postDelayed({

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(userInput.text.toString(), passInput.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful) {
                            progressbar.setState(true) { // executed after animation end
                                //handler.postDelayed({
                                val intent = Intent(this, MenuAdmin::class.java)
                                startActivity(intent)
                            }
                        } else {
                            startError(progressbar)
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("Ocurrio un error, intentalo de nuevo")
                            builder.setPositiveButton("Aceptar", null)
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }

                        //},1500)

                },900)
            }
            else{
                Toast.makeText(this,"Llena los campos", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
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

