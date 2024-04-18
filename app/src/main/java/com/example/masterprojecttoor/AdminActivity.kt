package com.example.masterprojecttoor

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.masterprojecttoor.databinding.ActivityAdminBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.UUID

class AdminActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdminBinding
    lateinit var reference: DatabaseReference

    lateinit var filePath: Uri
    lateinit var downloaduri: Uri

    private val PICK_IMAGE_REQUEST = 22

    // instance for firebase storage and StorageReference
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }
    @SuppressLint("SuspiciousIndentation")
    private fun initview() {

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        binding.txtgalary.setOnClickListener{

            SelectImage()

        }
        binding.txtuplod.setOnClickListener {

            uploadImage()

        }

        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(
            Color.parseColor("#0F9D58")
        )
        actionBar?.setBackgroundDrawable(colorDrawable)



        reference = FirebaseDatabase.getInstance().reference


        binding.btndisplay.setOnClickListener {

            var pakage = binding.edtpcakge.text.toString()
            var name = binding.edtname.text.toString()
            var price = binding.edtprice.text.toString().toInt()
            var days = binding.edtdays.text.toString().toInt()
            var mobile = binding.edtmobile.text.toString()
            var note = binding.edtnote.text.toString()
            var image=downloaduri.toString()

            Log.e("TAG", "initview: "+pakage+" "+name+" "+price+" "+days+" "+mobile+" "+note)

            var key = reference.child("PackageTb").push().key ?: ""

            var model = AdminModel(key, pakage, name, price, days,mobile,note,image)

            reference.child("PackageTb").child(key).setValue(model).addOnCompleteListener {

                if(it.isSuccessful) {

                    Toast.makeText(this, "record successfully", Toast.LENGTH_SHORT).show()
                    onBackPressed()

                }

            }.addOnFailureListener {
                    Log.e("TAG", "initview:" + it.message)
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun uploadImage() {

        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref = storageReference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast.makeText(this@AdminActivity, "Image Uploaded!!", Toast.LENGTH_SHORT).show()

                    ref.downloadUrl.addOnSuccessListener {

                        downloaduri=it
                        Log.e("TAG", "uploadImage: "+it )
                    }

                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast.makeText(this@AdminActivity, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress = (100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount)
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }
        }
    }
    private fun SelectImage() {

        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data!!
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                binding.imgimgView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }
}



class AdminModel {
    var id = ""
    var packge = ""
    var name = ""
    var price = 0
    var days = 0
    var mobile= ""
    var note = ""
    var imageurl=""

    constructor(id: String, pcakge: String, name: String, price: Int, days: Int,mobile:String, note: String,imageurl:String) {

        this.id = id
        this.packge = pcakge
        this.name = name
        this.price = price
        this.days = days
        this.mobile=mobile
        this.note = note
        this.imageurl=imageurl
    }
    constructor()
    {

    }
}
