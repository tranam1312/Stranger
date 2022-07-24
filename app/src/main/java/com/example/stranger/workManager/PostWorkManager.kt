package com.example.stranger.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.stranger.common.State
import com.example.stranger.repository.Repository
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.flow.collect

class PostWorkManager(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val repository = Repository()

    override suspend fun doWork(): Result {

       return repository.upLoadAnh(data, repository.getKey()).collect {
           when (it){
               is State.Success -> {
                 Result.success(it.data)
               }
           }
        } as Result

    }





}