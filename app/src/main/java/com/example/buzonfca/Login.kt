package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*



class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        entrar.setOnClickListener {
            if(userInput.text!!.isNotEmpty()  && passInput.text!!.isNotEmpty() ){

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(userInput.text.toString(), passInput.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this, MenuAdmin::class.java)
                            startActivity(intent)
                        }else{
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("Ocurrio un error, intentalo de nuevo")
                            builder.setPositiveButton("Aceptar",null)
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }

            }else{
                Toast.makeText(this,"Llena los campos", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}