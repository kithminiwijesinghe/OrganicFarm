package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference

    private lateinit var uploadEmail : EditText
    private lateinit var uploadFName : EditText
    private lateinit var uploadLName : EditText
    private lateinit var uploadMobile : EditText
    private lateinit var uploadStaffID : EditText
    private lateinit var btnValidate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uploadEmail = findViewById(R.id.uploadEmail)
        uploadFName = findViewById(R.id.uploadFName)
        uploadLName = findViewById(R.id.uploadLName)
        uploadMobile = findViewById(R.id.uploadMobile)
        uploadStaffID = findViewById(R.id.uploadStaffID)
        btnValidate = findViewById(R.id.sendButton)

        uploadEmail.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(uploadEmail.text.toString()).matches())
                    btnValidate.isEnabled = true
                else {
                    btnValidate.isEnabled = false
                    uploadEmail.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.sendButton.setOnClickListener {

            val staffId = binding.uploadStaffID.text.toString().trim()
            val firstname = binding.uploadFName.text.toString().trim()
            val lastname = binding.uploadLName.text.toString().trim()
            val number = binding.uploadMobile.text.toString().trim()
            val email = binding.uploadEmail.text.toString().trim()

            if (staffId.isEmpty()) {
                uploadEmail.error = "Staff ID Required.."
                return@setOnClickListener
            }else if (firstname.isEmpty()) {
                uploadFName.error = "First Name Required.."
                return@setOnClickListener
            }else if (lastname.isEmpty()) {
                uploadLName.error = "Last Name Required.."
                return@setOnClickListener
            }else if (number.isEmpty()) {
                uploadMobile.error = "Mobile Required.."
                return@setOnClickListener
            }else if (email.isEmpty()) {
                uploadEmail.error = "Email Required.."
                return@setOnClickListener
            }else {
                Toast.makeText(this,"Success..",Toast.LENGTH_SHORT).show()
            }

            database = FirebaseDatabase.getInstance().getReference("Staff")
            val users = Staff(staffId,firstname,lastname,number,email)
            database.child(staffId).setValue(users).addOnSuccessListener {

                binding.uploadEmail.text.clear()
                binding.uploadFName.text.clear()
                binding.uploadLName.text.clear()
                binding.uploadMobile.text.clear()

                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ProfileActivity, AdminDashboard::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}