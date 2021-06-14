package com.example.buzonfca

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu_admin.*

class MenuAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)



        //MUESTRA PANTALLA DE LOS REGISTROS DE LA BASE DE DATOS
        todobtn.setOnClickListener {
            val intent = Intent(this, FilteredData::class.java)
            startActivity(intent)
        }

        //CIERRA SESION Y REGRESA A LA PANTALLA PRINCIPAL
        logoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            super.finish()
//        }
//        return super.onKeyDown(keyCode, event)
//    }

//    override fun onBackPressed() {
//        moveTaskToBack(true)
//    }


    //metodo cerrar sesion y manual
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.item1) {
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }
//        if (id == R.id.item2) {
//            Toast.makeText(this, "Manual de usuario", Toast.LENGTH_SHORT).show()
//        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        val alerta = AlertDialog.Builder(this@MenuAdmin)

        alerta.setMessage("¿Desea cerrar sesión?")
                .setCancelable(false)
                .setPositiveButton("Si") { dialog, which ->

                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                }
                .setNegativeButton("No") { dialog, which -> dialog.cancel() }
        val titulo = alerta.create()
        titulo.setTitle("Cerrar sesión")
        titulo.show()

    }
}