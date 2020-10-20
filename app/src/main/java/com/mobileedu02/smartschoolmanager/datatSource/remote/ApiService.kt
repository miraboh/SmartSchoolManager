package com.mobileedu02.smartschoolmanager.datatSource.remote

import com.mobileedu02.smartschoolmanager.model.Quiz
import retrofit2.http.GET

interface ApiService{

    @GET("/quiz.json")
    suspend fun getApiQuiz(): List<Quiz>
}