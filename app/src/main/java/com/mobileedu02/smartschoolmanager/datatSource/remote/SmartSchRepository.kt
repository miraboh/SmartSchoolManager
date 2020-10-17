package com.mobileedu02.smartschoolmanager.datatSource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.mobileedu02.smartschoolmanager.model.News
import com.mobileedu02.smartschoolmanager.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SmartSchRepository @Inject constructor(){

    private val mInstance = FirebaseFirestore.getInstance()
    private val getting = mInstance.collection("feeds").document("feedsId")


    @ExperimentalCoroutinesApi
    fun getNews() = flow<State<News?>> {

        // Emit loading state
        emit(State.loading())

        val snapshot = getting.get().await()
        val posts = snapshot.toObject(News::class.java)

        // Emit success state with data
        emit(State.success(posts))

    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)



}

