package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FilteredData : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var dbref : DatabaseReference
    private lateinit var dataRecyclerview : RecyclerView
    private lateinit var dataList : ArrayList<DBData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_data)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        dataRecyclerview = findViewById(R.id.dataList)
        dataRecyclerview.layoutManager = LinearLayoutManager(this)
        dataRecyclerview.setHasFixedSize(true)

        dataList = arrayListOf<DBData>()

        var cat: Spinner = findViewById(R.id.mostrar)
        cat.onItemSelectedListener = this

        var orden: Spinner = findViewById(R.id.ordenar)
        orden.onItemSelectedListener = this


    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MenuAdmin::class.java)
        startActivity(intent)
        return true
    }


    private fun getUserData(path : String, value : String) {

        dbref = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    if(path != "order"){ //si ordena por recientes

                        dataList.clear()

                    }
                    for (userSnapshot in snapshot.children){
                        val dato = userSnapshot.getValue(DBData::class.java)

                        if (dato != null) {


                            if(path == "Categoria" && dato.Categoria == value) {

                                dataList.add(dato)

                                }else if(path == "Status" && dato.Status == value || path == "Status" &&  dato.Status!!.take(9) == value) {

                                    dataList.add(dato)


                                }else if (path == "" && value == ""){

                                    dataList.add(dato)

                                }

                        }

                    }

                    var adapt = Adapter(dataList)
                    dataRecyclerview.adapter = adapt


                     adapt.setOnClickListener(object : Adapter.onItemClickListener{


                        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        }

                        override fun onItemClick(position: Int) {

                            var pos = (position)
                            var c = dataList[pos].Comentario

                            val intent = Intent(this@FilteredData, SelectedQS::class.java)
                            intent.putExtra("comentario",c)
                            startActivity(intent)

                            }

                        })

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {

            //var ver = parent.selectedItemPosition //posicion de la opcion seleccionada
            var ver = parent.selectedItem.toString()

            if (ver == "Quejas" || ver == "Sugerencias") { // si queja o sugerencia, quiere decir que es una categoria
                var ver = parent.selectedItem.toString() //recoge el valor si es queja o sugerencia
                ver = ver.dropLast(1) //al estar en plural las palabras le elimina la ultima s de la palabra para hacer la busqueda
                getUserData(path = "Categoria", value = ver) //manda a la funcion para buscar y guardar los datos requeridos

            } else if (ver == "Pendientes" || ver == "Resueltos") { // si resuelto o pendiente, quiere decir que es status
                var ver = parent.selectedItem.toString() //recoge el valor si es resuelto o pendiente
                ver = ver.dropLast(1) //al estar en plural las palabras le elimina la ultima s de la palabra para hacer la busqueda

                getUserData(path = "Status", value = ver) //manda a la funcion para buscar y guardar los datos requeridos

            }else if(ver == "Antiguos"){

                getUserData(path = "order", value = "") //manda a la funcion para mostrar los registros
            }else if(ver == "Recientes"){

                getUserData(path = "", value = "") //manda a la funcion para mostrar los registros
            }

            else{

                getUserData(path = "", value = "") //manda a la funcion para mostrar todos los registros
            }

        }

    }
}