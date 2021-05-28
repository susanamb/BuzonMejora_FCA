package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_data_updated_view.*

class DataUpdatedView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_updated_view)

        done.setOnClickListener {
            val intent = Intent(this, MenuAdmin::class.java)
            startActivity(intent)
        }
    }


}