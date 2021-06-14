package com.example.buzonfca

import android.app.Activity
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



class DataView : AppCompatActivity(){

    private lateinit var dbref : DatabaseReference
    private lateinit var dataRecyclerview : RecyclerView
    private lateinit var dataList : ArrayList<DBData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_view)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = ""

            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        dataRecyclerview = findViewById(R.id.dataList)
        dataRecyclerview.layoutManager = LinearLayoutManager(this)
        dataRecyclerview.setHasFixedSize(true)

        dataList = arrayListOf<DBData>()
        getUserData()


    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MenuAdmin::class.java)
        startActivity(intent)
        return true
    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
        dbref.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val dato = userSnapshot.getValue(DBData::class.java)
                        if (dato != null) {
                            dataList.add(dato)
                        }
                    }

                    var adapt = Adapter(dataList)
                    dataRecyclerview.adapter = adapt

                    adapt.setOnClickListener(object : Adapter.onItemClickListener{

                        override fun onItemClick(position: Int) {
                            val folio = (position+1).toString()

                            val intent = Intent(this@DataView, SelectedQS::class.java)
                            intent.putExtra("folio",folio)
                            startActivity(intent)
                        }

                        override fun onItemClick(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }



}


