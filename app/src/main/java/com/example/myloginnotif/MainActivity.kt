package com.example.myloginnotif

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myloginnotif.databinding.ActivityLoginBinding
import com.example.myloginnotif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        }

        prefManager = PrefManager.getInstance(this)
        val name = prefManager.getusername()
        checkLoginStatus()
        with(binding){
            welcome.text = welcome.text.toString().plus(name)
        }
    }
    private fun checkLoginStatus(){
        if (prefManager.getusername().isEmpty()){
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }
    }
}