package com.example.myapplication20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch20_firebase.util.myCheckPermission
import com.example.myapplication20.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val keyHash = Utility.getKeyHash(this)
        // Log.d("mobileApp", keyHash)
        myCheckPermission(this)
        binding.addFab.setOnClickListener {
            if (MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            }
            else {
                Toast.makeText(this, "인증 진행해 주세요..", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            if (binding.btnLogin.text.equals("Login"))
                intent.putExtra("data", "logout")
            else if (binding.btnLogin.text.equals("Logout"))
                intent.putExtra("data", "login")
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (MyApplication.checkAuth() || MyApplication.email != null) {
            binding.btnLogin.text = "Logout"
            // binding.authTv.text = "Hello, ${MyApplication.email}"
            binding.authTv.textSize = 16F
            binding.mainRecyclerView.visibility = View.GONE
            makeRecyclerView()
        }
        else {
            binding.btnLogin.text = "Login"
            binding.authTv.text = "Shannon"
            binding.authTv.textSize = 24F
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

    private fun makeRecyclerView() {
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}