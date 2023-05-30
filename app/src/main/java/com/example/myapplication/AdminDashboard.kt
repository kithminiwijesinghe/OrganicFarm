package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityAdminDashboardBinding

class AdminDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.d1.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@AdminDashboard, ProfileActivity::class.java)
            startActivity(intent)
        })

        binding.d2.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@AdminDashboard, ViewProfile::class.java)
            startActivity(intent)
        })

        binding.d3.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@AdminDashboard, UpdateProfiles::class.java)
            startActivity(intent)
        })

        binding.d4.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@AdminDashboard, DeleteProfiles::class.java)
            startActivity(intent)
        })

    }
}