package com.example.buzonfca

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Quejas y Sugerencias")
        var contador =0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val con = this
        val internet = isOnline(con)
        if (internet){
            Toast.makeText(this, "TAS CONECTAO", Toast.LENGTH_SHORT).show()
            Log.d("Hello", " THERES WIFI")
        }else{
            Toast.makeText(this, "NO TAS CONECTAO", Toast.LENGTH_SHORT).show()
            Log.d("Hello", " THERES NOO WIFI")
            iniciarbtn.isEnabled = false
            val alerta = AlertDialog.Builder(this@MainActivity)
            alerta.setMessage("No tienes conexion a internet")
            val titulo = alerta.create()
            titulo.setTitle("Error")
            titulo.show()
        }

        //BOTON PARA PANTALLA DE REALIZAR UNA NUEVA QUEJA O SUGERENCIA
        iniciarbtn.setOnClickListener{
            val intent = Intent(this, Form1::class.java)
            startActivity(intent)
        }

        //BOTON PARA PANTALLA DE CONSULTA DE FOLIO
        consul.setOnClickListener{
            val intents = Intent(this, ActivityMenu1::class.java)
            startActivity(intents)
        }

        //BOTON PARA PANTALLA DE INICIO DE SESION DE S

        //CUENTA LA CANTIDAD DE CASOS CON EL ESTATUS RESUELTO
        val query: Query = myRef.orderByChild("Status").equalTo("Resuelto")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (issue in dataSnapshot.children) {
                        contador += 1
                    }
                }
                textView17.text = contador.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        help.setOnClickListener {
            val inten = Intent(this, ManualUsuario::class.java)
            startActivity(inten)
        }
    }


    override fun onBackPressed() {

            val alerta = AlertDialog.Builder(this@MainActivity)
            alerta.setMessage("¿Desea salir de la aplicación?")
                    .setCancelable(false)
                    .setPositiveButton("Si") { dialog, which -> finish() }
                    .setNegativeButton("No") { dialog, which -> dialog.cancel() }
            val titulo = alerta.create()
            titulo.setTitle("Salida")
            titulo.show()


}
   private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
 /*private fun internetConnection(){
     val context = this
     val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
     val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
     val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

     if(isConnected){
         Toast.makeText(this, "There's wifi", Toast.LENGTH_SHORT).show()
     }else{
         Toast.makeText(this, "There's NO wifi", Toast.LENGTH_SHORT).show()
     }

 }*/

}