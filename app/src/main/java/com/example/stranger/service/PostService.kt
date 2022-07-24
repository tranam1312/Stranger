package com.example.stranger.service


import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.hilt.work.HiltWorker
import com.example.stranger.common.State
import com.example.stranger.repository.Repository
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect

@HiltWorker
class PostService : Service() {
    val scope = CoroutineScope(Job() + Dispatchers.IO)

    private  val repository : Repository =  Repository()
    override  fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
         val imge= intent.extras?.get("image")
        val content  = intent.extras?.get("content")
        upAnh(imge as ByteArray)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    fun upAnh(imge :ByteArray) {
       scope.launch {
          repository.upLoadAnh(imge,repository.getKey()).collect{
              when(it){
                  is State.Success-> {
                      
                  }
                  is State.Progress -> {

                  }
                  is State.Error -> {

                  }
              }
          }

       }
        }

}

