package com.example.listbyapi

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

internal class CustomAdapter(private var data: List<Offre>): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    var onItemClick : ((Offre)-> Unit)? = null
    internal inner class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        var txtNom: TextView = itemView.findViewById(R.id.txtNom)
        var txtGender : TextView = itemView.findViewById(R.id.txtGender)
        var txtDuree : TextView = itemView.findViewById(R.id.txtDuree)
        init {
            view.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
                txtNom.setTextColor(Color.RED)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.line,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ite= data[position]
        holder.txtNom.text= ite.nomfilm
        holder.txtGender.text= ite.genre
        holder.txtDuree.text= ite.duree


    }

    override fun getItemCount(): Int {
        return data.size
    }
}