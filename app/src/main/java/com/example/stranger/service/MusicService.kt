package com.example.stranger.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.stranger.R
import com.example.stranger.model.Song
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class MusicService : Service() {
    private lateinit var mediaSessionCompat: MediaSessionCompat
    val CHANNEL_ID = "Music"
    private  var mSong: Song? = null
    private val mediaPlayer = MediaPlayer()
    private lateinit var notificationCompat: NotificationCompat.Builder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSong = intent?.extras?.get("listSong") as Song
        if (mSong != null) {
            createNotificationChannel(mSong!!)
            mediaPlayer.setSong(mSong!!)
            mediaPlayer.getRecouse()
        }
        val actionMusicReceiver: Int = intent?.getIntExtra("action_music_service", 0) as Int
        handleActionMusic(actionMusicReceiver)
        return START_STICKY
    }

    private fun createNotificationChannel(song: Song) {
        mediaSessionCompat = MediaSessionCompat(this, "tag")
        startForeground(2, build(song, mediaSessionCompat).build())
    }

    fun build(song: Song, mediaSessionCompat: MediaSessionCompat): NotificationCompat.Builder {
        notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentText(song.performer)
            .setContentTitle(song.name)
        if (mediaPlayer.getIsPlay()) {
            notificationCompat.addAction(
                R.drawable.ic_skip_back,
                "Previous",
                pendingIntent(this, ACTION_PREVIOUS)
            )
                .addAction(R.drawable.ic_pause, "Pause", pendingIntent(this, ACTION_RESUM))
                .addAction(R.drawable.ic_skip_forward, "Next", pendingIntent(this, ACTION_NEXT))
                .addAction(R.drawable.ic_x_black, "Close", pendingIntent(this, ACTION_CLOSE))
        } else {
            notificationCompat.addAction(
                R.drawable.ic_skip_back,
                "Previous",
                pendingIntent(this, ACTION_PREVIOUS)
            )
                .addAction(R.drawable.ic_play, "Play", pendingIntent(this, ACTION_PLAY))
                .addAction(R.drawable.ic_skip_forward, "Next", pendingIntent(this, ACTION_NEXT))
                .addAction(R.drawable.ic_x_black, "Close", pendingIntent(this, ACTION_CLOSE))
        }
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSessionCompat.sessionToken)
            )
        song.thumbnail?.let {
            loadImg(it, object : loadImg {
                override fun sucssec(bitmap: Bitmap) {
                    notificationCompat.setLargeIcon(bitmap)
                }

            })
        }
        return notificationCompat
    }

    fun pendingIntent(context: Context, action: Int): PendingIntent {
        val intent = Intent(applicationContext, MusicReceiver::class.java)
        intent.putExtra("action_music", action)
        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun handleActionMusic(action: Int) {
        when (action) {
            ACTION_PREVIOUS -> mediaPlayer.previousMusic()
            ACTION_PLAY -> {
                sendActionActivity(ACTION_PLAY)
                mediaPlayer.playMusic()
                createNotificationChannel(mSong!!)
            }
            ACTION_RESUM -> {
                mediaPlayer.resumMusic()
                sendActionActivity(ACTION_RESUM)
                createNotificationChannel(mSong!!)
            }
            ACTION_NEXT -> mediaPlayer.nextMusic()
            ACTION_CLOSE -> {
                mediaPlayer.closeMusic()
                sendActionActivity(ACTION_CLOSE)
            }
        }
    }

    fun sendActionActivity(action: Int) {
        val intent = Intent("send_data_to_activity")
        val bundle = Bundle()
        bundle.putSerializable("song", mSong)
        bundle.putBoolean("status", mediaPlayer.getIsPlay())
        bundle.putInt("action", action)
        intent.putExtras(bundle)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    fun loadImg(src: String, loadImg: loadImg) {
        val thread = Thread {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            loadImg.sucssec(BitmapFactory.decodeStream(input))
        }
        thread.start()


    }

    override fun onDestroy() {
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        val ACTION_PREVIOUS = 1
        val ACTION_PLAY = 2
        val ACTION_RESUM = 3
        val ACTION_NEXT = 4
        val ACTION_CLOSE = 5

    }

    interface loadImg {
        fun sucssec(bitmap: Bitmap)
    }
}