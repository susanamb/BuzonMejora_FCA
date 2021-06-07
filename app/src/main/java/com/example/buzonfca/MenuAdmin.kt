package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu_admin.*

class MenuAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)



        //MUESTRA PANTALLA DE LOS REGISTROS DE LA BASE DE DATOS
        todobtn.setOnClickListener {
            val intent = Intent(this, FilteredData::class.java)
            startActivity(intent)
        }

        //CIERRA SESION Y REGRESA A LA PANTALLA PRINCIPAL
        logoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}