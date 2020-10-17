package com.mobileedu02.smartschoolmanager.ui.fragments.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.databinding.HomeFragmentBinding
import com.mobileedu02.smartschoolmanager.util.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(inflater)

        binding.libraryId.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_libraryFragment)
        }

        binding.txtNewsFeed.isSelected=true

        GlobalScope.launch(Dispatchers.Main) {
            loadNews()
        }

        return binding.root
    }
    @ExperimentalCoroutinesApi
    private suspend fun loadNews() {
        viewModel.getNews().collect { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(context,"Loading News",Toast.LENGTH_LONG).show()
                }

                is State.Success -> {

                    binding.txtNewsFeed.text=state.data!!.info

                }

                is State.Failed ->{
                    Toast.makeText(context,"News couldn't load",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}