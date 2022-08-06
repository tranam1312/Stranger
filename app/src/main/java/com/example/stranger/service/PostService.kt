package com.example.stranger.service


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.stranger.R
import com.example.stranger.common.State
import com.example.stranger.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostService : Service() {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    private val CHANNEL_ID = "POST_NOTIFICATION"
    private val repository: Repository = Repository()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val bitmap = intent.extras?.get("image")
        val content = intent.extras?.get("content")

        startForeground(10, build(bitmap as ByteArray).build())
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun build(data: ByteArray): NotificationCompat.Builder {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library


        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentText("Đang tạo 0 % ")
            .setProgress(100, 0, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.post_notifi)
            val descriptionText = getString(R.string.channel_post_notifi)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
            upAnh(data, builder)
        }
        return builder
    }

    fun upAnh(data: ByteArray, build: NotificationCompat.Builder) {
        build.setContentTitle("Đang tạo bài viết")
        scope.launch {
            repository.upLoadAnh(data, repository.getKey()).collect {
                when (it) {
                    is State.Success -> {
                        build.setContentTitle("Đã tải lên hoàn tất")
                    }
                    is State.Progress -> {
                        build.setContentText("Đâng tạo ${it.data} %")
                        build.setProgress(
                            100,
                            if (it.data.toIntOrNull() == null) 0 else it.data.toInt(),
                            true
                        )
                    }
                    is State.Error -> {
                        build.setContentTitle("Nhấp để tải lên")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(1)
    }

}

