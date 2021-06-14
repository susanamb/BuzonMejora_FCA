package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro_usuario.*
import kotlinx.android.synthetic.main.activity_registro_usuario.passInput
import kotlinx.android.synthetic.main.activity_registro_usuario.userInput
import java.util.regex.Pattern

class RegistroUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        //BOTON PARA REGRESAR
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }//FIN BOTON REGRESO

        save.setOnClickListener {
            if(userInput.text!!.isNotEmpty()  && passInput.text!!.isNotEmpty() && confirmpassInput.text!!.isNotEmpty()) {//validar que todos los campos esten llenos
                val mail = userInput.text.toString()
                val uabcmail = validarEmail("$mail")
                if(uabcmail) {//valida que el correo introducido sea de uabc

                   if (passInput.text.toString() == confirmpassInput.text.toString()) {//validar que haya introducido la misma contrasena en ambos inputs

                       FirebaseAuth.getInstance().createUserWithEmailAndPassword(userInput.text.toString(),passInput.text.toString())//crea el usuario en Firebase
                           .addOnCompleteListener{
                               if(it.isSuccessful){
                                   val res = "Guardado"
                                   val mensaje = "Usuario registrado exitosamente"
                                   showAlert(mensaje,res)

                                   val intent = Intent(this, Login::class.java)
                                   startActivity(intent)

                               }else{
                                   val res = "Error"
                                   val mensaje = "Se produjo un error al guardar, intentelo de nuevo"
                                   showAlert(mensaje,res)
                               }
                           }

                   } else {

                       Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                   }
               }else{
                    Toast.makeText(this,"Correo invalido, debe ser UABC", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Llena los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarEmail(email: String): Boolean { //valida que sea un correo uabc
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return if(pattern.matcher(email).matches()){ //valida que sea un correo
            val parts: Array<String> = email.split('@').toTypedArray() //separa correo para recoger el dominio
            val dominio = parts[1] // guarda el valor del dominio
            dominio == "uabc.edu.mx" //devuelve true si el correo es de uabc
        }else{
            false
        }
    }
    private fun showAlert(mensaje : String, res : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("$res")
        builder.setMessage("$mensaje")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    //REGRESAR A LA PANTALLA ANTERIOR
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}