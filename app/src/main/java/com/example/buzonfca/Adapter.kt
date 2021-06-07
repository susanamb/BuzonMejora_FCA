package com.example.buzonfca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val dataList : ArrayList<DBData>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener 

    interface onItemClickListener{

        fun onItemClick(position: Int)
        fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long)
    }

    fun setOnClickListener(listener : onItemClickListener){

        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.queja_sugerencia, parent,false)
        return MyViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = dataList[position]

        holder.categoria.text = currentitem.Categoria
        holder.asunto.text = currentitem.Asunto
        holder.status.text = currentitem.Status

    }

    override fun getItemCount(): Int {

        return dataList.size
    }


    class MyViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val categoria : TextView = itemView.findViewById(R.id.categoria)
        val asunto : TextView = itemView.findViewById(R.id.asunto)
        val status : TextView = itemView.findViewById(R.id.status)

        init{

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }

}