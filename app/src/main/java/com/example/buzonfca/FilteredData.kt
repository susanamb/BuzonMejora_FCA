package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
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
            actionBar.title = "Menu"

            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        dataRecyclerview = findViewById(R.id.dataList)
        dataRecyclerview.layoutManager = LinearLayoutManager(this)
        dataRecyclerview.setHasFixedSize(true)

        dataList = arrayListOf<DBData>()

        var cat: Spinner = findViewById(R.id.mostrar)
        cat.onItemSelectedListener = this


    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MenuAdmin::class.java)
        startActivity(intent)
        return true
    }


    private fun getUserData(path : String, value : String) {
        dbref = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
        dbref.addValueEventListener(object : ValueEventListener {
        //val query : Query = dbref.orderByChild("$path").startAt("$value")
        //query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val dato = userSnapshot.getValue(DBData::class.java)

                        if (dato != null) {
                            if(path == "Categoria" && dato.Categoria == value) {
                                dataList.add(dato)
                            }else if(path == "Status" && dato.Status == value) {
                                dataList.add(dato)
                            }else{
                                dataList.add(dato)
                            }
                        }

                    }

                    var adapt = Adapter(dataList)
                    dataRecyclerview.adapter = adapt

                    adapt.setOnClickListener(object : Adapter.onItemClickListener{

                        override fun onItemClick(position: Int) {
                            val folio = (position+1).toString()

                            val intent = Intent(this@FilteredData, SelectedQS::class.java)
                            intent.putExtra("folio",folio)
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

            var ver = parent.selectedItem.toString()
            Toast.makeText(this, "Elegiste $ver", Toast.LENGTH_SHORT).show()

            if (ver == "Queja" || ver == "Sugerencia") {
                getUserData(path = "Categoria", value = ver)

            } else if (ver == "Pendiente" || ver == "Resuelto") {
                getUserData(path = "Status", value = ver)

            }else{
                getUserData(path = "", value = "")
            }


        }

    }
}