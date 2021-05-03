package com.example.buzonfca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}