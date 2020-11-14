package com.mobileedu02.smartschoolmanager.datatSource.remote

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mobileedu02.smartschoolmanager.datatSource.local.SmartSchoolDao
import com.mobileedu02.smartschoolmanager.datatSource.local.SmartSchoolDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)

object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        val okkHttpclient = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .client(okkHttpclient)
            .baseUrl("https://smart-school-manager.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
    //commitment is the key

    @Singleton
    @Provides
    fun buildDatabase(@ApplicationContext context: Context): SmartSchoolDb{
        return Room.databaseBuilder(
            context,
            SmartSchoolDb::class.java,
            SmartSchoolDb.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: SmartSchoolDb): SmartSchoolDao {
        return database.smartSchoolDao
    }

    @Singleton
    @Provides
    fun provideRepository(
        dao: SmartSchoolDao,
        retrofit: ApiService
    ): SmartSchRepository{
        return SmartSchRepository(dao, retrofit)
    }

}