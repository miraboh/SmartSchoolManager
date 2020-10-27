package com.mobileedu02.smartschoolmanager.ui.fragments.result

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobileedu02.smartschoolmanager.databinding.FragmentResultsBinding
import com.mobileedu02.smartschoolmanager.model.Users

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

                // Ebo this is the object to be displayed
                binding.txtResult.text = user!!.score.toString()
                //you can add other stuffs like the name like below etc
                //binding.txtResult.text = user.surName.toString()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadUser:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabaseReference!!.child(userId!!).addListenerForSingleValueEvent(userListener)
    }
}