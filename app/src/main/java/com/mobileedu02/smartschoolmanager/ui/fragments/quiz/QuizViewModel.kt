package com.mobileedu02.smartschoolmanager.ui.fragments.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mobileedu02.smartschoolmanager.datatSource.remote.SmartSchRepository
import com.mobileedu02.smartschoolmanager.model.History
import com.mobileedu02.smartschoolmanager.model.Quiz
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizViewModel @Inject constructor(
    private val smartSchRepository: SmartSchRepository
    ) : ViewModel() {
    private val viewModelJob = SupervisorJob()

    val questionslist: LiveData<List<Quiz>>? = smartSchRepository.downloadedQuiz



    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                smartSchRepository.refreshQuiz()

            } catch (networkError: IOException) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun insert(history: History) {

        viewModelScope.launch {

            smartSchRepository.insertHistory(history)
        }
    }
}