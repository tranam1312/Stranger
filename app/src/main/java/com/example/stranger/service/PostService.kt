package com.example.stranger.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.stranger.R
import com.example.stranger.common.State
import com.example.stranger.extension.getTimeDate
import com.example.stranger.model.ItemHome
import com.example.stranger.repository.Repository
import com.example.stranger.ui.home.post.PostFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostService : Service() {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    private val CHANNEL_ID = "POST_NOTIFICATION"
    private val repository: Repository = Repository()
    private var content: String? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val bitmap = intent.extras?.get("image")
        content = intent.extras?.get("content") as String?
        build(bitmap as ByteArray)
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun build(data: ByteArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.post_notifi)
            val descriptionText = getString(R.string.channel_post_notifi)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Đang tạo bài viết")
            .setSmallIcon(R.drawable.ic_bell)
        notification.setProgress(
            100, 0, false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        upAnh(data, notification)
        startForeground(1, notification.build())
    }

    private fun upAnh(data: ByteArray, notification: Notification.Builder) {
        scope.launch {
            repository.upLoadAnh(data, repository.getKey()).collect {
                when (it) {
                    is State.Success -> {
                        newPost(url = it.data)
                    }
                    is State.Progress -> {
                        notification.setContentTitle("Đâng tạo ${it.data} %")
                        notification.setProgress(
                            100,
                            if (it.data.toIntOrNull() == null) 0 else it.data.toInt(),
                            false
                        )
                        startForeground(1, notification.build())

                    }
                    is State.Error -> {
                        notification.setContentTitle("Nhấp để tải lên")
                        startForeground(1, notification.build())


                    }
                }
            }
        }
    }

    private fun newPost(url: String) {
        val itemHome = ItemHome(
            repository.getKey(),
            null,
            repository.getUid(),
            content,
            url,
            null,
            null,
            System.currentTimeMillis()
        )
        scope.launch {
            repository.upLoadContentPosts(itemHome).collect {
                when (it) {
                    is State.Success -> {
                        stopForeground(1)
                        postNotif()
                    }
                    is State.Error -> {

                    }
                }
            }
        }
    }

    fun postNotif() {

        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Đã tạo xong bài viết")
            .setContentText("Chạm để mở bài viết")
            .setSmallIcon(R.drawable.ic_bell)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.post_notifi)
            val descriptionText = getString(R.string.channel_post_notifi)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
        NotificationManagerCompat.from(this).apply {
            this.notify(getTimeDate().toInt(), builder.build())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(1)
    }


}

