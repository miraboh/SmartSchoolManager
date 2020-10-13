package com.mobileedu02.smartschoolmanager.ui.fragments.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobileedu02.smartschoolmanager.model.Book
import com.mobileedu02.smartschoolmanager.model.Chapter
import kotlinx.coroutines.SupervisorJob

class LibraryViewModel : ViewModel() {

    private val viewModelJob = SupervisorJob()

    var books: List<Book> = arrayListOf(
        Book( "Applied Engineering Mathematics",
            listOf(
                Chapter("Chapter 1 Introduction to Algebra", "Introduction to Algebra"),
                Chapter("Chapter 2 Introduction to Simultaneous Equations", "Introduction to Simultaneous Equations"),
                Chapter("Chapter 3", "Introduction to Set and Theory"),
                Chapter("Chapter 4", "Introduction to Calculus"))),
        Book( "Physics fo Astronomers",
            listOf(
                Chapter("Chapter 1", "Introduction to Physics blablabla"),
                Chapter("Chapter 2", "Introduction to blablabla Physics"),
                Chapter("Chapter 3", "Introduction to Physics blablabla"),
                Chapter("Chapter 4", "Introduction to blablabla Physics"),
                Chapter("Chapter 5", "Introduction to Physics blablabla"))),
        Book( "Advance level English Language",
            listOf(
                Chapter("Chapter 1", "English Verbs"),
                Chapter("Chapter 2", "English Pronoun"),
                Chapter("Chapter 3", "English Noun"),
                Chapter("Chapter 4", "English Adverb"),
                Chapter("Chapter 5", "English Adjectives"),
                Chapter("Chapter 6", "English Consonant"),
                Chapter("Chapter 7", "English Vowels"))),
        Book( "Applied Chemistry",
            listOf(
                Chapter("Chapter 1", "Introduction to Chemistry"))),
        Book( "Advance Literature in English",
            listOf(Chapter("Chapter 1", "Introduction to Literature"))))

    private val _downloadedBooks = MutableLiveData<List<Book>>()
    val downloadedBooks: LiveData<List<Book>>
        get() = _downloadedBooks

    init {
        _downloadedBooks.postValue(books.onEach { it.title })
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}