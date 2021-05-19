package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD:app/src/main/java/com/example/buzonfca/MainActivity.kt
=======
import android.util.Log.i
import android.view.View

>>>>>>> 1b2d70806a6f4d7fd08282dd61cafc10361dcd6e:app/src/main/java/com/example/prototipo/MainActivity.kt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val iniciarbtn = findViewById<iniciarbtn>(R.id.Button)

        iniciarbtn.setOnClickListener{
            val intent = Intent(this, Form1::class.java)
            startActivity(intent)
        }

        consul.setOnClickListener{
            val intents = Intent(this, ActivityMenu1::class.java)
            startActivity(intents)
        }

        log.setOnClickListener {
            val intent2 = Intent(this, login::class.java)
            startActivity(intent2)
        }
    }


}