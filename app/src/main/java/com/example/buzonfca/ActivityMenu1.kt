package com.example.buzonfca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


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