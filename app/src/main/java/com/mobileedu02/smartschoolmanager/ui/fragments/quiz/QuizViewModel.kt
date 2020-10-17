package com.mobileedu02.smartschoolmanager.ui.fragments.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mobileedu02.smartschoolmanager.datatSource.remote.SmartSchRepository
import kotlinx.coroutines.*
import java.io.IOException

class QuizViewModel(private val smartSchRepository: SmartSchRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
