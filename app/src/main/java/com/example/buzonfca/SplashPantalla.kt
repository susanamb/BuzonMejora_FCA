package com.example.buzonfca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buzonfca.R
import android.content.Intent
import android.os.Handler
import com.example.buzonfca.MainActivity

 //VISTA DE INICIO CON ICONO DE LA FCA X 2 SEG
class SplashPantalla : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_pantalla)
        Handler().postDelayed({
            val intent = Intent(this@SplashPantalla, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}