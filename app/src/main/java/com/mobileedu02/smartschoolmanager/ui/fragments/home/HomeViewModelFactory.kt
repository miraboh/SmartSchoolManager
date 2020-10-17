package com.mobileedu02.smartschoolmanager.ui.fragments.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileedu02.smartschoolmanager.datatSource.remote.SmartSchRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory (private val smartSchRepository: SmartSchRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(smartSchRepository) as T
    }
}