package com.example.myapplication20

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication20.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    // lateinit var filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendBtn.setOnClickListener {
            Log.d("mobileApp", "click Btn")
            Log.d("mobileApp", "${binding.answerEditTv.text}")
            if (binding.answerEditTv.text.toString() == "1") {
                binding.showUserAnswer.visibility = View.VISIBLE
                binding.showInfo.visibility = View.VISIBLE
                binding.showUserAnswer.text = "1"
                binding.showInfo.text = "Here's the link for checking nearby Doctors: "
                val uri = Uri.parse("https://www.shannonhealth.com/provider-directory/")
                val it = Intent(Intent.ACTION_VIEW, uri)
                startActivity(it)
                Log.d("mobileApp", "click Btn 1")
            } else if (binding.answerEditTv.text.toString() == "2") {
                binding.showUserAnswer.visibility = View.VISIBLE
                binding.showInfo.visibility = View.VISIBLE
                binding.showUserAnswer.text = "2"
                binding.showInfo.text = "Here's our newest health news: "
                val uri = Uri.parse("https://www.shannonhealth.com/health-library/")
                val it = Intent(Intent.ACTION_VIEW, uri)
                startActivity(it)
            } else if (binding.answerEditTv.text.toString() == "3") {
                binding.showUserAnswer.visibility = View.VISIBLE
                binding.showInfo.visibility = View.VISIBLE
                binding.showUserAnswer.text = "3"
                binding.showInfo.text = "Here's our visiting hours: "
                val uri = Uri.parse("https://www.shannonhealth.com/patients-and-visitors/visiting-hours/")
                val it = Intent(Intent.ACTION_VIEW, uri)
                startActivity(it)
            }
            else if (binding.answerEditTv.text.toString() == "4") {
                binding.showUserAnswer.visibility = View.VISIBLE
                binding.showInfo.visibility = View.VISIBLE
                binding.showUserAnswer.text = "4"
                binding.showInfo.text = "Here you can find nearest locations: "
                val uri = Uri.parse("https://www.shannonhealth.com/locations/")
                val it = Intent(Intent.ACTION_VIEW, uri)
                startActivity(it)
            } else {
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_add, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//    val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        if (it.resultCode === android.app.Activity.RESULT_OK) {
//            Glide
//                .with(applicationContext)
//                .load(it.data?.data)
//                .apply(RequestOptions().override(250, 200))
//                .centerCrop()
//                .into(binding.addImageView)
//            val cursor = contentResolver.query(it.data?.data as Uri,
//                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null)
//            cursor?.moveToFirst().let {
//                filePath = cursor?.getString(0) as String
//            }
//        }
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId === R.id.menu_add_gallery) {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
//            requestLauncher.launch(intent)
//        }
//        else if (item.itemId === R.id.menu_add_save) {
//            if (binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()) {
//                saveStore()
//            }
//            else {
//                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun saveStore() {
//        val data = mapOf(
//            "email" to MyApplication.email,
//            "content" to binding.addEditView.text.toString(),
//            "date" to dateToString(Date())
//        )
//
//        MyApplication.db.collection("news")
//            .add(data)
//            .addOnSuccessListener {
//                uploadImage(it.id)
//            }
//            .addOnFailureListener {
//                Log.d("mobileApp", "data save error")
//            }
//    }
//
//    private fun uploadImage(docId: String) {
//        val storage = MyApplication.storage
//        val storageRef = storage.reference
//        val imageRef = storageRef.child("images/${docId}.jpg")
//
//        val file = Uri.fromFile(File(filePath))
//        imageRef.putFile(file)
//            .addOnSuccessListener {
//                Toast.makeText(this, "save ok..", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//            .addOnFailureListener {
//                Log.d("mobileApp", "file save error")
//            }
//    }
}