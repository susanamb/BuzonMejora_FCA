package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_folio_view.*
import kotlinx.android.synthetic.main.activity_form1.*
import kotlinx.android.synthetic.main.activity_login.*

class FolioView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folio_view)

        val folio = intent.getStringExtra("Folio")

        textView11.text = folio

        submit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}