package com.example.masterprojecttoor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler // Import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.masterprojecttoor.databinding.ActivitySplshScreenBinding

class SplshScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplshScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
    }

    private fun initview() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
    }
}
