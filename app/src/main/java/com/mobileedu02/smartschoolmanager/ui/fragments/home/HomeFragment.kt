package com.mobileedu02.smartschoolmanager.ui.fragments.home

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

    private var user: FirebaseUser? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    private var mProgressBar: ProgressDialog? = null

    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(inflater)

        mProgressBar = ProgressDialog(context)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference
        user = FirebaseAuth.getInstance().currentUser

        binding.myHistoryId.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
        }

        binding.libraryId.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_libraryFragment)
        }

        binding.resultId.setOnClickListener {
            if (user != null) {
                findNavController().navigate(R.id.action_homeFragment_to_resultsFragment)
            }else{
                Toast.makeText(context,"You are not registered", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_homeFragment_to_signUpFragment)
            }
        }

        binding.txtNewsFeed.isSelected=true
        //binding.txtNewsFeed.scrollBarFadeDuration = 1

        GlobalScope.launch(Dispatchers.Main) {
            loadNews()
        }

        binding.takeQuizId.setOnClickListener {
            if (user != null) {
                mProgressBar!!.setMessage("Starting Quiz...")
                mProgressBar!!.setCanceledOnTouchOutside(false)
                mProgressBar!!.show()
                //binding.takeQuizId.isEnabled = false
                Handler().postDelayed(Runnable {
                    mProgressBar!!.hide()
                    activity?.let {
                        findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
                    }
                },3000)
            }else{
                Toast.makeText(context,"You are not registered", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_homeFragment_to_signUpFragment)
            }
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
                    Toast.makeText(context,"Latest News couldn't load",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}