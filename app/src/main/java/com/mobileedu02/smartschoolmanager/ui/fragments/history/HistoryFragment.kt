package com.mobileedu02.smartschoolmanager.ui.fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.mobileedu02.smartschoolmanager.databinding.HistoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: HistoryFragmentBinding
    @Inject
    lateinit var viewModel: HistoryViewModel
    private var txt: TextView? = null

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HistoryFragmentBinding.inflate(inflater)


        txt = binding.txtView
        viewModel.historyTransformedData.observe(
            viewLifecycleOwner, Observer {
                txt!!.text = it
            }
        )

        return binding.root
    }
}