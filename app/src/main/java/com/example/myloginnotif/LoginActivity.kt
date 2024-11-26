package com.example.myloginnotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myloginnotif.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var prefManager: PrefManager
    private var usernameAccount = "Ujang"
    private var passwordAccount = "qwerty"
    private val channelId = "LOGIN_NOTIF"
    private val notifId = 90

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        }

        prefManager = PrefManager.getInstance(this)
        with(binding){
            submit.setOnClickListener {
                val usernameInput = username.text.toString()
                val paswordInput = password.text.toString()

                if (usernameInput == usernameAccount && paswordInput == passwordAccount){
                    prefManager.saveusername(usernameInput)
                    checkLoginStatus()
                }else{
                    Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun checkLoginStatus(){
        val username = prefManager.getusername()
        val intent = Intent(this, LogoutReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (username.isNotEmpty()){
            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_login)
                .setContentTitle("Notif masuk aplikasi")
                .setContentText("Tekan button logout untuk keluar")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(0, "LOGOUT", pendingIntent)

            val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    channelId,
                    "Login notif",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                with(notifManager) {
                    createNotificationChannel(notifChannel)
                    notify(0, builder.build())
                }
            }
            else {
                notifManager.notify(0, builder.build())
            }
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }
}