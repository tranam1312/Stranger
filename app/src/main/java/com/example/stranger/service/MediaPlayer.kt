package com.example.stranger.service

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.stranger.model.Song

class MediaPlayer(){
    private lateinit var song: Song
    private var mediaPlayer: MediaPlayer? = null
    private var isPlay:Boolean = false

    fun getIsPlay(): Boolean{
        return isPlay
    }
    fun previousMusic(){

    }

    fun release(){
        mediaPlayer!!.release()
        mediaPlayer = null
    }
    fun playMusic(){
        if (mediaPlayer!= null&& !isPlay){
            mediaPlayer!!.start()
            isPlay= true
        }
    }
    fun resumMusic(){
        if (mediaPlayer != null && isPlay){
            mediaPlayer!!.pause()
            isPlay = false
        }
    }
    fun nextMusic(){

    }
    fun closeMusic(){
        if (mediaPlayer !== null && !isPlay){
            mediaPlayer!!.stop()
        }
    }
    fun setSong(song: Song){
        this.song = song
    }
    fun getRecouse(){
        if (mediaPlayer ==null) {
            val url = "http://api.mp3.zing.vn/api/streaming/audio/${song.id}/320"
           mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                prepare()
                start()
                isPlay = true
            }
        }
        else{
            val url = "http://api.mp3.zing.vn/api/streaming/audio/${song.id}/320"
            mediaPlayer!!.release()
             mediaPlayer =MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                prepare()
                start()
                isPlay = true
            }
        }
    }
}