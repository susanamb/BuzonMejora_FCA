package com.example.buzonfca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FilteredData : AppCompatActivity() {

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
        getUserData()


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
        //dbref.addValueEventListener(object : ValueEventListener {
        val query : Query = dbref.orderByChild("Status").startAt("Pendiente,")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

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
                            val folio = position.toString()

                            val intent = Intent(this@FilteredData, SelectedQS::class.java)
                            intent.putExtra("folio",folio)
                            startActivity(intent)

                        }
                    })
                }
            }

        })

    }



}