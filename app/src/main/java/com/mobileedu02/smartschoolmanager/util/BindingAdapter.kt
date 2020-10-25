package com.mobileedu02.smartschoolmanager.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileedu02.smartschoolmanager.model.Book
import com.mobileedu02.smartschoolmanager.ui.fragments.library.LibraryAdapter

@BindingAdapter("listData")
fun bindBooksRecyclerView(booksRecyclerView: RecyclerView, data: List<Book>?) {
    val adapter = booksRecyclerView.adapter as LibraryAdapter
    adapter.submitList(data)

}