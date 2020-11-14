package com.mobileedu02.smartschoolmanager.ui.fragments.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.mobileedu02.smartschoolmanager.datatSource.local.SmartSchoolDao
import com.mobileedu02.smartschoolmanager.util.formatHistory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HistoryViewModel @Inject constructor(smartSchoolDao: SmartSchoolDao,
                       application: Application): AndroidViewModel(application){

    val history = smartSchoolDao.getAllHistory()


        val historyTransformedData = Transformations.map(history) {
            formatHistory(it, application.resources)
        }

}