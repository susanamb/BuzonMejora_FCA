package com.example.prototipo

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form1.*

class Form1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form1)




        //scroll del texto
       val desc = findViewById<View>(R.id.editTextTextMultiLine) as TextView
        desc.setMovementMethod(ScrollingMovementMethod());

        //BOTON PARA REGRESAR
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        //BOTON DE ENVIAR
        enviar.setOnClickListener {
            val intent = Intent(this, FolioView::class.java)
            startActivity(intent)
        }
        
        val cat: Spinner = findViewById(R.id.spinner)
        cat.onItemSelectedListener = this
    
        val asunto: Spinner = findViewById(R.id.spinner2)
        asunto.onItemSelectedListener = this
    }
    //RADIO BUTTONS

    //BACK BUTTON
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    //SPINNERS
    override fun onNothingSelected(parent: AdapterView<*>?) {
        textInputLayout3.visibility = View.INVISIBLE
        textInputLayout4.visibility = View.INVISIBLE
    }
    //SPINNERS ACTION
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        ///val a = R.id.spinner2
        //textView3.text = a.toString()
        if (parent != null) {
            var pos = parent.selectedItemPosition
            if(pos == (parent.count)-1){
                textInputLayout3.visibility = View.VISIBLE
            }else{
                textInputLayout3.visibility = View.INVISIBLE
            }
        }

    }

}