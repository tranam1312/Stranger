package com.example.stranger.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.stranger.R
import com.example.stranger.model.response.Song
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicService : Service() {
    private lateinit var mediaSessionCompat: MediaSessionCompat
    val CHANNEL_ID = "Music"
    private var listSong: ArrayList<Song> = arrayListOf()
    private var position: Int = 0
    private lateinit var notificationCompat: NotificationCompat.Builder
    private var mediaPlayer: MediaPlayer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        listSong = intent?.extras?.get("listSong") as ArrayList<Song>
        position = intent?.extras?.get("position") as Int
        if (listSong.size > 0) {
            setRecourse(listSong[position])
            createNotificationChannel(listSong[position])
        }
        val actionMusicReceiver: Int = intent?.getIntExtra("action_music_service", 0) as Int
        handleActionMusic(actionMusicReceiver)
        return START_STICKY
    }

    private fun createNotificationChannel(song: Song) {
        mediaSessionCompat = MediaSessionCompat(this, "tag")
        startForeground(1, build(song, mediaSessionCompat).build())
    }

    fun build(song: Song, mediaSessionCompat: MediaSessionCompat): NotificationCompat.Builder {
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
        notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentText(song.performer)
            .setContentTitle(song.name)
        if (mediaPlayer?.isPlaying == true) {
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
            ACTION_PREVIOUS -> {
                mediaPlayer
            }
            ACTION_PLAY -> {
                sendActionActivity(ACTION_PLAY)
                mediaPlayer?.start()
                createNotificationChannel(listSong[position]!!)
            }
            ACTION_RESUM -> {
                mediaPlayer?.pause()
                sendActionActivity(ACTION_RESUM)
                createNotificationChannel(listSong[position]!!)
            }
            ACTION_NEXT -> {
                mediaPlayer
            }
            ACTION_CLOSE -> {
                stopSelf()
            }
        }
    }

    fun sendActionActivity(action: Int) {
        val intent = Intent("send_data_to_activity")
        val bundle = Bundle()
        bundle.putSerializable("song", listSong.get(position))
        bundle.putBoolean("status", mediaPlayer?.isPlaying == true)
        bundle.putInt("action", action)
        intent.putExtras(bundle)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    fun setRecourse(mSong: Song) {
        val url = "http://api.mp3.zing.vn/api/streaming/audio/${mSong.id}/320"
        if (mediaPlayer != null) mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                it.start()
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        listSong = arrayListOf()
        stopForeground(1)
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
}