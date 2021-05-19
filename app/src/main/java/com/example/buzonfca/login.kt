package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        entrar.setOnClickListener {
            val intent = Intent(this, DataView::class.java)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}