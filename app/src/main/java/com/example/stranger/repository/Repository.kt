package com.example.stranger.repository

import android.content.ClipData
import android.content.ContentValues.TAG
import android.util.Log
import com.example.stranger.common.State
import com.example.stranger.common.State.Success
import com.example.stranger.model.ProFile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Singleton
class Repository{
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference.child("appData")
    val proFileDatabase = firebaseDatabase.child("profile")
    val storageRef = FirebaseStorage.getInstance()
    private lateinit var uploadTask : UploadTask

    fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
    fun getKey():String{
        return firebaseDatabase.push().key.toString()
    }

    fun login(email: String, password: String): Flow<State<FirebaseUser?>> = callbackFlow {
        trySend(State.Loading)

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                trySendBlocking(Success(firebaseAuth.currentUser))
            }else {
                trySendBlocking(State.Error(it.exception?.message.toString()))
            }
        }.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }
    fun signUp(email: String, password: String): Flow<State<FirebaseUser?>> = callbackFlow {
        trySend(State.Loading)
        val onCompleteListener = OnCompleteListener<AuthResult> {
            if (it.isSuccessful) {
                trySendBlocking(State.Success(firebaseAuth.currentUser))
            } else {
                trySendBlocking(State.Error(it.exception?.message.toString()))
                Log.d(TAG, "login: fales")
            }
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener)
        awaitClose {
            close()
        }
    }

    fun getUid() : String? = firebaseAuth.uid

    fun getProFile(uid: String): Flow<State<ProFile?>> = callbackFlow {
        trySend(State.Loading)
        proFileDatabase.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                trySendBlocking(Success(snapshot.getValue(ProFile::class.java)))
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(State.Error(error.message))
            }

        })
        awaitClose {
            close()
        }
    }
    fun upDateProFile(uid: String, proFile: ProFile) : Flow<State<ProFile>> = callbackFlow {
        trySend(State.Loading)
        proFileDatabase.child(uid).setValue(proFile).addOnCompleteListener{
            trySendBlocking(Success(proFile))
        }.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }
        awaitClose{
            close()
        }
    }

    fun upLoadAnh(data: ByteArray,key: String): Flow<State<String>> = callbackFlow{
        val storage: StorageReference = storageRef.reference.child("${firebaseAuth.currentUser?.uid}").child(
            key)
        uploadTask = storage.putBytes(data)
        uploadTask.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }.addOnProgressListener {
            val progress : Int = ((100.0 * it.bytesTransferred) / it.totalByteCount).toInt()
            trySendBlocking(State.Progress(progress.toString()))
        }.addOnSuccessListener {
            storage.downloadUrl.addOnCompleteListener { task ->
                trySendBlocking(Success(task.result.toString()))
            }
        }
    }
    fun postItemHome():Flow<State<{

    }
}


