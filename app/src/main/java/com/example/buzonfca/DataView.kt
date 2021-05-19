package com.example.buzonfca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DataView : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var dataRecyclerview : RecyclerView
    private lateinit var dataList : ArrayList<DBData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_view)
        dataRecyclerview = findViewById(R.id.dataList)
        dataRecyclerview.layoutManager = LinearLayoutManager(this)
        dataRecyclerview.setHasFixedSize(true)

        dataList = arrayListOf<DBData>()
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Quejas y Sugerencias")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val dato = userSnapshot.getValue(DBData::class.java)
                        dataList.add(dato!!)

                    }
                    dataRecyclerview.adapter = Adapter(dataList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}