package com.example.masterproject.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterprojecttoor.AdminModel
import com.example.masterprojecttoor.ImageSliderActivity
import com.example.masterprojecttoor.R
import com.example.masterprojecttoor.databinding.PackgeDisplayBinding
import com.google.firebase.database.ValueEventListener

class PakageAdapter(var context: ValueEventListener, var datalist: ArrayList<AdminModel>) : RecyclerView.Adapter<PakageAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: PackgeDisplayBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.packge_display, parent, false)
        var holder = MyViewHolder(PackgeDisplayBinding.bind(v))
        return holder

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = datalist[position]


        Glide.with(holder.itemView.context).load(currentItem.imageurl).into(holder.binding.imagimage)
        holder.binding.txtpackge.text = currentItem.packge
        holder.binding.txtname.text = currentItem.name
        holder.binding.txtprice.text = currentItem.price.toString()
        holder.binding.txtdays.text = currentItem.days.toString()
        holder.binding.txtnote.text = currentItem.note

        holder.itemView.setOnClickListener {
            var i=Intent(holder.itemView.context,ImageSliderActivity::class.java)
            i.putExtra("pakage",currentItem.packge)
            i.putExtra("name",currentItem.name)
            i.putExtra("price",currentItem.price)
            i.putExtra("days",currentItem.days)
            i.putExtra("note",currentItem.note)
            i.putExtra("moilbe",currentItem.mobile)

            holder.itemView.context.startActivity(i)}
    }
}