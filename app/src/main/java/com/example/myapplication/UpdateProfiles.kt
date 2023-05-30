package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityUpdateProfilesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateProfiles : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfilesBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.searchButton.setOnClickListener {
            val rStaffID = binding.staffID.text.toString()
            searchRequest(rStaffID)
        }

        binding.updateButton.setOnClickListener {
            val rStaffID = binding.staffID.text.toString()
            val rUpdateFName = binding.updateFName.text.toString()
            val rUpdateLName = binding.updateLName.text.toString()
            val rUpdateEmail = binding.updateAddress.text.toString()
            val rUpdateMobile = binding.updateMobile.text.toString()
            updateData(rStaffID,rUpdateFName,rUpdateLName,rUpdateEmail,rUpdateMobile)
        }
    }

    private fun searchRequest(rStaffID: String) {
        if (rStaffID.isBlank()) {
            Toast.makeText(this, "Please enter a Staff ID", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Staff")
        database.child(rStaffID).get().addOnSuccessListener { snapshot ->
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

    //to update data
    private fun updateData(staffId: String, firstname: String, lastname: String,number: String, email: String) {

        //validate all fields are filled
        if (staffId.isBlank() || firstname.isBlank() ||lastname.isBlank()|| number.isBlank() || email.isBlank()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            return
        }

        //validate mobile number
        if (number.length != 10) {
            Toast.makeText(this, "Number should contain 10 digits", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase.getInstance().getReference("Staff")
        database.child(staffId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val staff = mapOf<String,String>(
                    "firstname" to firstname,
                    "lastname" to lastname,
                    "number" to number,
                    "email" to email
                )
                database.child(staffId).updateChildren(staff).addOnSuccessListener {
                    clearFields()
                    Toast.makeText(this, "Successfully updated Profile", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to update Request", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to search for Profile", Toast.LENGTH_SHORT).show()
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

