package com.example.masterprojecttoor

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.masterprojecttoor.Adapter.ImageSliderAdapter
import com.example.masterprojecttoor.databinding.ActivityImageSliderBinding

class ImageSliderActivity : AppCompatActivity() {
    lateinit var binding: ActivityImageSliderBinding

    var list: ArrayList<Int> = ArrayList()
    lateinit var txtYes: TextView
    lateinit var txtNo: TextView
    lateinit var txtMobiles: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {

        var pakage = intent.getStringExtra("pakage")
        var name = intent.getStringExtra("name")
        var price = intent.getIntExtra("price", 0)
        var days = intent.getIntExtra("days", 0)
        var mobile = intent.getStringExtra("moilbe")
        var note = intent.getStringExtra("note")


        binding.txtpakage.text = pakage
        binding.txtnames.text = name
        binding.txtprices.text = price.toString()
        binding.txtdays.text = days.toString()
        binding.txtcall.setText(mobile)
        binding.txtnotes.text = note




        list.add(R.drawable.junagadh)
        list.add(R.drawable.dwarka)
        list.add(R.drawable.bhavnagar)
        list.add(R.drawable.palitana)

        var adaptor = ImageSliderAdapter(list, this)
        binding.viewPager.adapter = adaptor

        binding.lincallnow.setOnClickListener {

            val dialog = Dialog(this@ImageSliderActivity)

            dialog.setContentView(R.layout.call_item)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.window?.attributes?.windowAnimations = R.style.animation

            txtYes = dialog.findViewById(R.id.txtyes)
            txtNo = dialog.findViewById(R.id.txtno)
            txtMobiles = dialog.findViewById(R.id.txtnumber)

            txtMobiles.text = mobile
            txtYes.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$mobile")
                startActivity(intent)
            }

            txtNo.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }



        binding.btnnext.setOnClickListener {

            var i = Intent(this@ImageSliderActivity, BooknowActivity::class.java)
            i.putExtra("number",mobile)
            i.putExtra("pakage",pakage)
            startActivity(i)
        }

    }
}