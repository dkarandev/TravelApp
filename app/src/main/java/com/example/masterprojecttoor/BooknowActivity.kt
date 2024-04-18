package com.example.masterprojecttoor

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.masterprojecttoor.databinding.ActivityBooknowBinding
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import java.util.Calendar

class BooknowActivity : AppCompatActivity(), PaymentResultWithDataListener {

    lateinit var binding: ActivityBooknowBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityBooknowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {

        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")
        val address = sharedPreferences.getString("address", "")


        binding.edtfrom.setText(address)
        binding.edtemail.setText(email)
        binding.edtusername.setText(username)


        var mobile = intent.getStringExtra("number")
        var to = intent.getStringExtra("pakage")

        binding.edtMobile.setText(mobile)
        binding.edtto.setText(to)


        binding.btnbooktour.setOnClickListener {

            val activity: Activity = this

            Checkout.preload(applicationContext)
            val co = Checkout()
            // apart from setting it in AndroidManifest.xml, keyId can also be set
            // programmatically during runtime
            co.setKeyID("rzp_test_TlXJp6OMxTikN7")

            try {
                val options = JSONObject()
                options.put("name","Razorpay Corp")
                options.put("description","Demoing Charges")
                //You can omit the image option to fetch the image from the dashboard
                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
                options.put("theme.color", "#3399cc");
                options.put("currency","INR");
                options.put("order_id", "order_DBJOWzybf0sJbb");
                options.put("amount","50000")//pass amount in currency subunits

                val retryObj =  JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                val prefill = JSONObject()
                prefill.put("email","gaurav.kumar@example.com")
                prefill.put("contact","9876543210")

                options.put("profile",prefill)
                co.open(activity,options)


            }catch (e: Exception){
                Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

        }

        binding.edtdate.setOnClickListener {


            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a date picker dialog
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in a toast
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.edtdate.setText(selectedDate)
                Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
            }, year, month, day)

            // Show the date picker dialog
            dpd.show()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        TODO("Not yet implemented")
    }
}