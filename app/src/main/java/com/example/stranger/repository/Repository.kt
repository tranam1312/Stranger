package com.example.stranger.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.stranger.api.Api
import com.example.stranger.common.State
import com.example.stranger.common.State.Success
import com.example.stranger.di.NetworkBoundRepository
import com.example.stranger.model.*
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
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Repository @Inject constructor(private val api: Api) {
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("appData")
    val home: DatabaseReference = firebaseDatabase.child("Home")
    val proFileDatabase = firebaseDatabase.child("profile")
    val storageRef = FirebaseStorage.getInstance()
    private lateinit var uploadTask: UploadTask

    fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun getKey(): String {
        return firebaseDatabase.push().key.toString()
    }

  suspend fun login(email: String, password: String): Flow<State<FirebaseUser?>> = callbackFlow {
        trySend(State.Loading)

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                trySendBlocking(Success(firebaseAuth.currentUser))
            } else {
                trySendBlocking(State.Error(it.exception?.message.toString()))
            }
        }.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

  suspend  fun signUp(email: String, password: String): Flow<State<FirebaseUser?>> = callbackFlow {
        trySend(State.Loading)
        val onCompleteListener = OnCompleteListener<AuthResult> {
            if (it.isSuccessful) {
                trySendBlocking(State.Success(firebaseAuth.currentUser))
            } else {
                trySendBlocking(State.Error(it.exception?.message.toString()))
                Log.d(TAG, "login: fales")
            }
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener)
        awaitClose {
            close()
        }
    }

    fun getUid(): String? = firebaseAuth.uid

   suspend fun getProFile(uid: String): Flow<State<ProFile?>> = callbackFlow {
        trySend(State.Loading)
        proFileDatabase.child(uid).addValueEventListener(object : ValueEventListener {
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

   suspend fun upDateProFile(uid: String, proFile: ProFile): Flow<State<ProFile>> = callbackFlow {
        trySend(State.Loading)
        proFileDatabase.child(uid).setValue(proFile).addOnCompleteListener {
            trySendBlocking(Success(proFile))
        }.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

   suspend fun upLoadAnh(data: ByteArray?, key: String): Flow<State<String>> = callbackFlow {
        val storage: StorageReference =
            storageRef.reference.child("${firebaseAuth.currentUser?.uid}").child(key)
        uploadTask = storage.putBytes(data!!)
        uploadTask.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }.addOnProgressListener {
            val progress: Int = ((100.0 * it.bytesTransferred) / it.totalByteCount).toInt()
            trySendBlocking(State.Progress(progress.toString()))
        }.addOnSuccessListener {
            storage.downloadUrl.addOnCompleteListener { task ->
                trySendBlocking(Success(task.result.toString()))
            }
        }
        awaitClose {
            close()
        }
    }

    suspend fun upLoadContentPosts(itemHome: ItemHome): Flow<State<ItemHome>> = callbackFlow {
        trySend(State.Loading)
        home.child(itemHome.key.toString()).setValue(itemHome).addOnCompleteListener {
            trySendBlocking(Success(itemHome))
        }.addOnFailureListener {
            trySendBlocking(State.Error(it.message.toString()))
        }
        awaitClose {
            close()
        }
    }

    suspend fun getDataHome(): Flow<State<List<ItemHome>>> = callbackFlow {
        trySend(State.Loading)
        home.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listItemHome: ArrayList<ItemHome> = arrayListOf()
                (snapshot.value as? HashMap<*, *>)?.values?.forEach {
                    val a = it as HashMap<String, *>
                    listItemHome.add(
                        ItemHome(
                            datetime = a["datetime"] as Long,
                            userid = a["userid"].toString(),
                            content = a["content"].toString(),
                            key = a["key"].toString(),
                            urlImage = a["urlImage"].toString()
                        )
                    )
                }
                Collections.sort(listItemHome, Comparator<ItemHome> { p0, p1 ->
                    p0.datetime?.let { p1.datetime?.compareTo(it) }!!
                })

                trySendBlocking(Success(listItemHome))
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(State.Error(error.message))
            }
        })
        awaitClose {
            close()
        }
    }

    suspend fun getDataChange(): Flow<State<List<ItemHome>>> = callbackFlow {
        trySend(State.Loading)
        home.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listItemHome: ArrayList<ItemHome> = arrayListOf()
                (snapshot.value as? HashMap<*, *>)?.values?.forEach {
                    val a = it as HashMap<String, *>
                    listItemHome.add(
                        ItemHome(
                            datetime = a["datetime"] as Long,
                            userid = a["userid"].toString(),
                            content = a["content"].toString(),
                            key = a["key"].toString(),
                            urlImage = a["urlImage"].toString()
                        )
                    )
                }
                Collections.sort(listItemHome, Comparator<ItemHome> { p0, p1 ->
                    p0.datetime?.let { p1.datetime?.compareTo(it) }!!
                })
                trySendBlocking(Success(listItemHome))
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(State.Error(error.message))
            }
        })
        awaitClose {
            close()
        }
    }

    suspend fun updateItemHome(itemHome: ItemHome) {
        home.child(itemHome.key.toString()).setValue(itemHome)
    }

    suspend fun getItemHome(key: String): Flow<State<ItemHome>> = callbackFlow {
        trySend(State.Loading)
        home.child(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySendBlocking(Success(snapshot.getValue(ItemHome::class.java)as ItemHome))
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(State.Error(error.message))
            }

        })
        awaitClose {
            close()
        }
    }

    suspend fun getDataMusic(): Flow<State<Music>> {
        return object : NetworkBoundRepository<Music>() {
            override suspend fun fetchFromRemote(): Response<Music> {
                return api.getBxhZing()
            }
        }.asFlow()
    }

    suspend fun getDataCommentChange(keyItemHome: String): Flow<State<ArrayList<Comment>>> =
        callbackFlow {
            trySend(State.Loading)
            home.child(keyItemHome).child("listcomment").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listItemComment: ArrayList<Comment> = arrayListOf()
                    (snapshot.value as? HashMap<*, *>)?.values?.forEach {
                        val a = it as HashMap<String, *>
                        listItemComment.add(
                            Comment(
                                datetime = a["datetime"] as Long,
                                userId = a["userId"].toString(),
                                title = a["title"].toString(),
                                listLikeComment = a["listLikeComment"] as ArrayList<String>,
                                listReplyComment = a["listReplyComment"] as HashMap<String, ReplyComment>,
                                key = a["key"].toString(),
                                urlImg = a["urlImg"].toString()
                            )
                        )
                    }
                    Collections.sort(listItemComment, Comparator<Comment> { p0, p1 ->
                        p0.datetime?.let { p1.datetime?.compareTo(it) }!!
                    })
                    trySendBlocking(Success(listItemComment))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySendBlocking(State.Error(error.message))
                }
            })
            awaitClose {
                close()
            }
        }
}

