package com.example.masterprojecttoor.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.masterprojecttoor.R

class ImageSliderAdapter(var list:ArrayList<Int>,var context: Context):PagerAdapter() {

    override fun getCount(): Int {
        return list.size

    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }


    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var view  = LayoutInflater.from(context).inflate(R.layout.image_slider,container,false)

        var imageview : ImageView = view.findViewById(R.id.imageslider)
        imageview.setImageResource(list.get(position))


        container.addView(view)


        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {

        container.removeView(obj as View?)

    }
}