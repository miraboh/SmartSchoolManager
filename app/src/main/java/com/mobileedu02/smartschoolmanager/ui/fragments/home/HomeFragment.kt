package com.mobileedu02.smartschoolmanager.ui.fragments.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(inflater)

        binding.libraryId.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_libraryFragment)
        }

        return binding.root
    }

}