package com.mobileedu02.smartschoolmanager.datatSource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobileedu02.smartschoolmanager.model.History


@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class SmartSchoolDb : RoomDatabase() {

    abstract val smartSchoolDao: SmartSchoolDao

    /**
     * without hilt/dagger our roomdb should have looked like this
     */
////    companion object {
////
////        @Volatile
////        private var INSTANCE: SmartSchoolDb? = null
////
////        fun getInstance(context: Context): SmartSchoolDb {
////            synchronized(this) {
////                var instance = INSTANCE
////                if (instance == null) {
////                    instance = Room.databaseBuilder(
////                        context.applicationContext,
////                        SmartSchoolDb::class.java,
////                        "history_db"
////                    )
////                        .fallbackToDestructiveMigration()
////                        .build()
////                    INSTANCE = instance
////                }
////                return instance
////            }
////        }
////    }

    companion object{
        val DATABASE_NAME: String = "history_db"
    }

}
