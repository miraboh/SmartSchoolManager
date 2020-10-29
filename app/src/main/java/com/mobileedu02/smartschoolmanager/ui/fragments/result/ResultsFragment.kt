package com.mobileedu02.smartschoolmanager.ui.fragments.result

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.databinding.FragmentResultsBinding
import com.mobileedu02.smartschoolmanager.model.Users
import com.mobileedu02.smartschoolmanager.ui.activity.AboutUs
import com.mobileedu02.smartschoolmanager.ui.fragments.quiz.QuizFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : Fragment() {

    private lateinit var binding: FragmentResultsBinding
    private var mProgressBar: ProgressDialog? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultsBinding.inflate(inflater)

        mProgressBar = ProgressDialog(context)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        userId = mAuth!!.uid

        mProgressBar!!.setMessage("Fetching Result...")
        mProgressBar!!.setCanceledOnTouchOutside(false)
        mProgressBar!!.show()

        initUsers()

        return binding.root
    }
    private fun initUsers() {
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(Users::class.java)
                mProgressBar!!.hide()

                if (user == null) {
                    binding.txtResult.text = getString(R.string.no_score_message)
                } else{
                    val userResultMessage = "Hello ${user.otherNames.toString()}, you scored ${user.score.toString()} out of 20"
                    binding.txtResult.text = userResultMessage

                    val userReward = when (user.score){
                        in 15..20 -> "excellent performance"
                        in 8..14 -> "Good score but you can do better"
                        else -> "You performed quite poorly"
                    }
                    txtReward.text = userReward

                    val emoji = when (user.score){
                        in 15..20 -> R.drawable.emoji_excellent
                        in 8..14 -> R.drawable.emoji_happy
                        else -> R.drawable.emoji_sad
                    }
                    img_view_emoji.setImageResource(emoji)

                    if (user.score in 0..14 ) {try_again_btn.visibility = View.VISIBLE}
                    try_again_btn.setOnClickListener { findNavController().navigate(R.id.quizFragment) }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadUser:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabaseReference!!.child(userId!!).addListenerForSingleValueEvent(userListener)
    }


}