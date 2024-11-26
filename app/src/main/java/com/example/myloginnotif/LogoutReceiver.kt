package com.example.myloginnotif

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class LogoutReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val prefManager = PrefManager.getInstance(context)
        prefManager.clear()

        Toast.makeText(context, "Anda keluar aplikasi", Toast.LENGTH_SHORT).show()

        // Hapus notifikasi
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

        // Pindah ke LoginActivity
        val loginIntent = Intent(context, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(loginIntent)
    }
}
