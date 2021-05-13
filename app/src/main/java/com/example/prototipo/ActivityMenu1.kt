package com.example.prototipo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buzonfca.R

class ActivityMenu1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu1)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
}