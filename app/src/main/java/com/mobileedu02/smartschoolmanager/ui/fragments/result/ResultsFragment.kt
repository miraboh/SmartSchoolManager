package com.mobileedu02.smartschoolmanager.ui.fragments.result

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.mobileedu02.smartschoolmanager.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    private lateinit var binding: FragmentResultsBinding
    private var mProgressBar: ProgressDialog? = null
    //Creating member variables

    private var mFirebaseDatabase: DatabaseReference?=null
    private var mFirebaseInstance: FirebaseDatabase?=null
    private lateinit var database: DatabaseReference

    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
// ...


    private var user: FirebaseUser? = null
    private val menu: MutableList<User> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultsBinding.inflate(inflater)

        mProgressBar = ProgressDialog(context)
        //Getting instances of FirebaseDatabase
        //Getting instances of FirebaseDatabase
        mFirebaseInstance = FirebaseDatabase.getInstance()

        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("Users")


        //val user = FirebaseAuth.getInstance().currentUser

        //initSaladMenu()

        binding.txtResult.text = menu.toString()

        //database = Firebase.database.reference

        mProgressBar!!.setMessage("Fetching Result...")

        mProgressBar!!.setCanceledOnTouchOutside(false)

        mProgressBar!!.show()

        return binding.root
    }
    private fun initSaladMenu() {
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //menu.clear()
                //dataSnapshot.children.mapNotNullTo(menu) { it.getValue<User>(User::class.java) }

                val s = dataSnapshot.getValue<User>(User::class.java)
                mProgressBar!!.hide()
                binding.txtResult.text = s.toString()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mFirebaseDatabase!!.child("Users").addListenerForSingleValueEvent(menuListener)
    }
}