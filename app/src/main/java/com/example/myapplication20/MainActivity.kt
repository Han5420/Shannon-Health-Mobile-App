package com.example.myapplication20

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch20_firebase.util.myCheckPermission
import com.example.myapplication20.databinding.ActivityMainBinding


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
                startActivity(Intent(this, AddActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            if (binding.btnLogin.text.equals("Login"))
                intent.putExtra("data", "logout")

            else if (binding.btnLogin.text.equals("Logout"))
                intent.putExtra("data", "login")
            startActivity(intent)

        }

        binding.myChart.setOnClickListener{
            if (MyApplication.checkAuth()) {
                val i = Intent(Intent.ACTION_VIEW)
                val url = "https://mychart.shannonhealth.org/mychart/Authentication/Login?"
                i.setData(Uri.parse(url));
                startActivity(i)
            }
            else {
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show()
            }

           }

        binding.viewPayBill.setOnClickListener{
            if (MyApplication.checkAuth()) {
                val wb = WebView(this)
                wb.loadUrl("file:///android_asset/index.html")
                setContentView(wb)
            }
            else {
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show()
            }

        }

        binding.viewPrescription.setOnClickListener{
            if (MyApplication.checkAuth()) {
                val wb = WebView(this)
                wb.loadUrl("file:///android_asset/prescription.html")
                setContentView(wb)
            }
            else {
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show()
            }
        }

        binding.dailySurvey.setOnClickListener{
            if (MyApplication.checkAuth()) {
                val wb = WebView(this)
                wb.loadUrl("file:///android_asset/surveyform.html")
                setContentView(wb)
            }
            else {
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show()
            }
        }
        binding.dailySurvey.setVisibility(View.INVISIBLE)


    }

    override fun onStart() {
        super.onStart()

        if (MyApplication.checkAuth() || MyApplication.email != null) {
            binding.btnLogin.text = "Logout"
            // binding.authTv.text = "Hello, ${MyApplication.email}"
            binding.authTv.textSize = 16F
            // .mainRecyclerView.visibility = View.GONE
            binding.dailySurvey.setVisibility(View.VISIBLE)
            makeRecyclerView()
        }
        else {
            binding.btnLogin.text = "Login"
            binding.authTv.text = "Shannon"
            binding.authTv.textSize = 24F
            // binding.mainRecyclerView.visibility = View.GONE
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
                // binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                // binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to get server data", Toast.LENGTH_SHORT).show()
            }
    }
}
