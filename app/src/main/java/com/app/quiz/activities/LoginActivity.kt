package com.app.quiz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.app.quiz.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.etEmailAddress
import kotlinx.android.synthetic.main.activity_signup.etPassword

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        val btnSignUp = findViewById(R.id.btnSignUp) as TextView

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login(){
            val email =etEmailAddress.text.toString()
            val password = etPassword.text.toString()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Email and Password can't be empty.", Toast.LENGTH_SHORT).show()
                return
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Login Successful.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else if(password.length < 6){
                    Toast.makeText(this, "Password must have at least 6 characters.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }

        }
}