package com.mobileedu02.smartschoolmanager.datatSource.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomdbModule {

    @Singleton
    @Provides
    fun provideHistoryDb(@ApplicationContext context: Context): SmartSchoolDb {
        return Room
            .databaseBuilder(
                context,
                SmartSchoolDb::class.java,
                SmartSchoolDb.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideHistoryDAO(smartSchoolDb: SmartSchoolDb): SmartSchoolDao {
        return smartSchoolDb.smartSchoolDao
    }

}
