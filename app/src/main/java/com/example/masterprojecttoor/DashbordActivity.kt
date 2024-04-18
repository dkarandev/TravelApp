package com.example.masterprojecttoor

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.masterproject.Fragment.HomeFragment
import com.example.masterprojecttoor.databinding.ActivityDashbordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashbordActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth


    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityDashbordBinding

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context


        fun initialize(context: Context) {
            this.context = context
        }

        val sharedPref by lazy { context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        }
        val UserAdmin: Int get() = (sharedPref.getInt("UserAdmin", 0) ?: "") as Int
    }

    lateinit var sharedPreferences: SharedPreferences

    lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashbordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)

        Companion.initialize(this)
        initview()


    }

    override fun onResume() {
        super.onResume()
        getProfileFromFirebase()
    }

    override fun onStart() {
        super.onStart()
        getProfileFromFirebase()
    }

    override fun onRestart() {
        super.onRestart()

        getProfileFromFirebase()
    }

    @SuppressLint("SetTextI18n")
    private fun initview() {


        // version display

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = packageInfo.versionName
        val versionCode = packageInfo.versionCode

        // Display the version name and version code in TextViews
        binding.txtversionname.text = "Version Name: $versionName"
        binding.txtversioncode.text = "Version Code:$versionCode"


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, HomeFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.txthome.setOnClickListener {

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame, HomeFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        binding.linhome.setOnClickListener {

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame, HomeFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        binding.linprivacy.setOnClickListener {

            val url = "https://www.termsfeed.com/live/f8b313f9-7708-4060-a751-c8315240801e"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

        }

        binding.txtlogout.setOnClickListener {
            onBackPressed()

        }

        binding.imgmenu.setOnClickListener {
            binding.drawerlayout.openDrawer(binding.navigation)
        }

        val isNewUser = sharedPref.getBoolean("isLogin", false)

        getProfileFromFirebase()
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("are you sure logout")
        builder.setTitle("Alert !")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {

                dialog, which ->

            var myEdit: SharedPreferences.Editor = sharedPreferences.edit()
            myEdit.remove("isLogin")
            myEdit.commit()
            auth.signOut()
            var intent = Intent(this@DashbordActivity, LoginActivity::class.java)
            Toast.makeText(this@DashbordActivity, "Successfully Logged Out", Toast.LENGTH_SHORT)
                .show()
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }



    private fun getProfileFromFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance()

        auth = FirebaseAuth.getInstance()

        auth.currentUser?.let {
            firebaseDatabase.reference.root.child("UserTb").child("username")
                .addValueEventListener(object :
                    ValueEventListener {
                    @SuppressLint("SetTextI18n")
                    override fun onDataChange(snapshot: DataSnapshot) {

                        sharedPreferences =
                            getSharedPreferences("MySharedPreferences", MODE_PRIVATE)

                        // Replace "your_key" with the actual key you used to store the data
                        val usernames = sharedPreferences.getString("username", "")
                        val emails = sharedPreferences.getString("email", "")
                        val image = sharedPreferences.getString("imageDownloadUrl", "")

                        var usertype = sharedPreferences.getInt("UserAdmin", 0)

                        Log.e("TAG", "onDataChangess: " + usertype)

                        var sharedPreferences: SharedPreferences.Editor = sharedPreferences.edit()
                        val userTypeText = when (usertype) {
                            0 -> "User"
                            else -> "Admin"
                        }
                        sharedPreferences.putInt("UserAdmin", usertype)

//                  Log.e("TAG", "onDataChange: ===> $username, $email, $image" )

                        binding.txtusername.text = "username: $usernames"
                        binding.txtemail.text = "email: $emails"
                        binding.txtuserAdmin.text = "usertype: $userTypeText"
                        Glide.with(this@DashbordActivity).load(image).into(binding.selecteimage)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@DashbordActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

}