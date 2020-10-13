package com.mobileedu02.smartschoolmanager.ui.fragments.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobileedu02.smartschoolmanager.databinding.BookListItemBinding
import com.mobileedu02.smartschoolmanager.model.Book

class LibraryAdapter: androidx.recyclerview.widget.ListAdapter<Book, LibraryAdapter.ViewHolder>(
    CoursesDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bk = getItem(position)
        holder.bind(bk)
    }

    class ViewHolder private constructor(private var binding: BookListItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.setClickListener {
                binding.book?.let { bk ->
                    navigateUni(bk, it)
                }
            }
        }

        private fun navigateUni(book: Book, it: View) {
            val direction = LibraryFragmentDirections.actionLibraryFragmentToBookDetailsFragment(book)
            it.findNavController().navigate(direction)
        }
        fun bind(item: Book) {
            binding.apply {
                book = item
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BookListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    class CoursesDiffUtilCallback: DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem

        }
    }
}