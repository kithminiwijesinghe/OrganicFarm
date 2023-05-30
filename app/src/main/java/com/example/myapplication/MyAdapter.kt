package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList : ArrayList<Staff>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.staffId.text = currentitem.staffId
        holder.firstName.text = currentitem.firstname
        holder.lastName.text = currentitem.lastname
        holder.email.text = currentitem.email
        holder.mobile.text = currentitem.number

    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val staffId : TextView = itemView.findViewById(R.id.tvStaffID)
        val firstName : TextView = itemView.findViewById(R.id.tvFName)
        val lastName : TextView = itemView.findViewById(R.id.tvLName)
        val mobile : TextView = itemView.findViewById(R.id.tvMobile)
        val email : TextView = itemView.findViewById(R.id.tvEmail)

    }

}