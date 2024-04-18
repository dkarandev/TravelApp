package com.example.masterproject.Fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterproject.Adapter.PakageAdapter
import com.example.masterprojecttoor.AdminActivity
import com.example.masterprojecttoor.AdminModel
import com.example.masterprojecttoor.DashbordActivity.Companion.UserAdmin
import com.example.masterprojecttoor.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentHomeBinding
    lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initview()

        return binding.root

    }

    private fun initview() {


        reference = FirebaseDatabase.getInstance().reference


        binding.add.setOnClickListener {


            var i = Intent(context, AdminActivity::class.java)
            startActivity(i)

        }

        if (UserAdmin == 0) {
            binding.add.visibility = View.GONE
        } else {
            binding.add.visibility = View.VISIBLE
        }

        reference.root.child("PackageTb").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var datalist: ArrayList<AdminModel> = ArrayList()

                for (data in snapshot.children) {
                    var pakagedata = data.getValue(AdminModel::class.java)


                    pakagedata?.let { datalist.add(it) }
                }


                var adapter = PakageAdapter(this, datalist)

                var manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.recyclerview.layoutManager = manager

                binding.recyclerview.adapter = adapter


            }
            override fun onCancelled(error: DatabaseError) {
            }

        })


        binding.add.setOnClickListener {


            var i = Intent(context, AdminActivity::class.java)
            startActivity(i)


        }

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
