package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityDeleteProfilesBinding
import com.example.myapplication.databinding.ActivityUpdateProfilesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteProfiles : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteProfilesBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.deleteButton.setOnClickListener {
            val rStaffID = binding.staffID.text.toString()
            if (rStaffID.isNotBlank()) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Delete Request")
                alertDialogBuilder.setMessage("Are you sure you want to delete this request?")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                    deleteData(rStaffID)
                }
                alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            } else {
                Toast.makeText(this, "Please enter a Staff ID", Toast.LENGTH_SHORT).show()
            }
        }


        binding.searchButton.setOnClickListener {
            val rStaffID = binding.staffID.text.toString()
            searchRequest(rStaffID)
        }

    }
    private fun deleteData(staffId: String) {
        database = FirebaseDatabase.getInstance().getReference("Staff")
        database.child(staffId).removeValue().addOnSuccessListener {
            binding.staffID.text.clear()
            binding.updateFName.text.clear()
            binding.updateLName.text.clear()
            binding.updateAddress.text.clear()
            binding.updateMobile.text.clear()
            Toast.makeText(this,"Successfully deleted profile", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to delete request", Toast.LENGTH_SHORT).show()
        }
    }

    //search box validation
    private fun searchRequest(number: String) {
        if (number.isBlank()) {
            Toast.makeText(this, "Please enter a Staff ID", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Staff")
        database.child(number).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val request = snapshot.value as? Map<String, Any>
                binding.updateFName.setText(request?.get("firstname").toString())
                binding.updateLName.setText(request?.get("lastname").toString())
                binding.updateAddress.setText(request?.get("number").toString())
                binding.updateMobile.setText(request?.get("email").toString())
                Toast.makeText(this, "Profile found", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to search for Profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.staffID.text.clear()
        binding.updateFName.text.clear()
        binding.updateLName.text.clear()
        binding.updateAddress.text.clear()
        binding.updateMobile.text.clear()
    }
}

