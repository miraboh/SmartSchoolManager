package com.mobileedu02.smartschoolmanager.ui.fragments.library

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.databinding.LibraryFragmentBinding

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private lateinit var viewModel: LibraryViewModel
    private lateinit var binding: LibraryFragmentBinding
    private lateinit var adapter: LibraryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LibraryFragmentBinding.inflate(inflater)
        adapter = LibraryAdapter()

        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.booksRecyclerView.adapter = adapter

        viewModel.downloadedBooks.observe(viewLifecycleOwner, Observer { newBooks ->
            if (newBooks == null){
                //Toast.makeText(context, "No data", Toast.LENGTH_LONG).show()
                return@Observer
            }
            newBooks.apply {
                //Toast.makeText(context, "$newBooks", Toast.LENGTH_LONG).show()
                adapter.submitList(newBooks)

            }
        })

        val manager = LinearLayoutManager(context)
        binding.booksRecyclerView.layoutManager = manager

        return binding.root
    }


}