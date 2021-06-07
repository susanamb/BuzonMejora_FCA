package com.example.buzonfca

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_folio_view.*

class FolioView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folio_view)

        val folio = intent.getStringExtra("Folio") //recupera el valor de folio dado en el Form1

        textView11.text = folio // muestra el folio

        //boton de aceptar regresa a la pantalla de inicio
        submit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("EditText", textView11.getText().toString())
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(this, "Folio copiado", Toast.LENGTH_SHORT).show()

        }









    }




}