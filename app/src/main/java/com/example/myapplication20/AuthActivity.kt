package com.example.myapplication20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myapplication20.databinding.ActivityAuthBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeVisibility(intent.getStringExtra("data").toString())
        binding.goSignInBtn.setOnClickListener {
            changeVisibility("signin")
        }

        binding.signBtn.setOnClickListener {
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()

            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if (task.isSuccessful){
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    Toast.makeText(baseContext, "successful! pleas check your mail", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }
                                else {
                                    Toast.makeText(baseContext, "failed to send mail", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }
                            }
                    }
                    else {
                        Toast.makeText(baseContext, "failed to create account", Toast.LENGTH_SHORT).show()
                        changeVisibility("logout")
                    }
                }
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            finish()
                        }
                        else {
                            Toast.makeText(baseContext, "Email authentication is not successful", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Toast.makeText(baseContext, "failed to login", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.logoutBtn.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null

            UserApiClient.instance.logout { error ->
                if (error != null) {
                    // Toast.makeText(baseContext, "logout failed", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(baseContext, "logout successful", Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }
    }

    fun changeVisibility(mode: String) {
        if (mode.equals("login")) {
            binding.run {
                // authMainTextView.text = "정말 로그아웃하시겠습니까?"
                // authMainTextView.visibility = View.VISIBLE
                logoutBtn.visibility = View.VISIBLE
                goSignInBtn.visibility = View.GONE
                authEmailEditView.visibility = View.GONE
                authPasswordEditView.visibility = View.GONE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.GONE
                // btnKakaoLogin.visibility = View.GONE
            }
        }
        else if (mode.equals("logout")) {
            binding.run {
                // authMainTextView.text = "로그인하거나 회원가입해 주세요."
                // authMainTextView.visibility = View.VISIBLE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
                // btnKakaoLogin.visibility = View.VISIBLE
            }
        }
        else if (mode.equals("signin")) {
            binding.run {
                // authMainTextView.visibility = View.GONE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
                // btnKakaoLogin.visibility = View.GONE
            }
        }
    }
}