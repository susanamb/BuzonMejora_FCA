package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu_admin.*

class MenuAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
    //metodo cerrar sesion y manual
   // override fun onCreateOptionsMenu(menu: Menu?): Boolean {
     //   menuInflater.inflate(R.menu.menu2, menu)
       // return true
    //}

    //override fun onOptionsItemSelected(item: MenuItem): Boolean {
      //  val id = item.itemId
        //if (id == R.id.item1) {
          //  FirebaseAuth.getInstance().signOut()
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
        //}
        //if (id == R.id.item2) {
          //  Toast.makeText(this, "Manual de usuario", Toast.LENGTH_SHORT).show()
        //}
        //return super.onOptionsItemSelected(item)
    //}
}