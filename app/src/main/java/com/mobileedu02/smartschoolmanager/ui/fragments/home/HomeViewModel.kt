package com.mobileedu02.smartschoolmanager.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.mobileedu02.smartschoolmanager.datatSource.remote.SmartSchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val smartSchRepository: SmartSchRepository): ViewModel(){

    @ExperimentalCoroutinesApi
    fun getNews() = smartSchRepository.getNews()

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}