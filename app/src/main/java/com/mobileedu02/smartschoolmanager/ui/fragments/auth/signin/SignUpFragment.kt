package com.mobileedu02.smartschoolmanager.ui.fragments.auth.signin

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobileedu02.smartschoolmanager.databinding.SignUpFragmentBinding
import com.mobileedu02.smartschoolmanager.util.hideKeyboard

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var binding: SignUpFragmentBinding

    private var etSurName: TextInputEditText? = null
    private var etOtherName: TextInputEditText? = null
    private var etPhoneNo: TextInputEditText? = null
    private var mProgressBar: ProgressDialog? = null
    private var btnCreateAccount: Button? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //global variables
    private var surName: String? = null
    private var otherNames: String? = null
    private var phoneNo: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SignUpFragmentBinding.inflate(inflater)

        initialise()

        return binding.root
    }

    private fun initialise() {
        etSurName = binding.surnameNameField
        etOtherName = binding.otherNamesField
        etPhoneNo = binding.phoneNameField

        btnCreateAccount = binding.registerButton
        mProgressBar = ProgressDialog(context)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnCreateAccount!!.setOnClickListener {
            createNewAccount()
            it.hideKeyboard(it)
        }
    }

    private fun createNewAccount() {
        surName = etSurName?.text.toString()
        otherNames = etOtherName?.text.toString()
        phoneNo = etPhoneNo?.text.toString()


        if (!TextUtils.isEmpty(surName) && !TextUtils.isEmpty(otherNames) && !TextUtils.isEmpty(phoneNo)
        ) {

            mProgressBar!!.setMessage("Registering User...")

            mProgressBar!!.setCanceledOnTouchOutside(false)

            mProgressBar!!.show()

            mAuth!!

                    //Once we get the users surname we are goig to use it to create a fake email address
                    //The aim is so that we don't request emails from users
                .createUserWithEmailAndPassword(surName + "xyz@gmail.com", otherNames.toString())

                .addOnCompleteListener { task ->
                    mProgressBar!!.hide()
                    if (task.isSuccessful) {

                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid
                        // We are doing this so that we can view users details and it will be helpful when storing users scores
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("surName").setValue(surName)
                        currentUserDb.child("otherNames").setValue(otherNames)
                        currentUserDb.child("phone").setValue(phoneNo)

                        activity?.let {
                            updateUserInfoAndUI()
                        }
                    } else {
                        // Allot of spaghetti code here right?
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        //Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        activity?.let {

                            val builder = AlertDialog.Builder(requireContext())

                            builder.setTitle("Registration Failed!")
                                .setMessage("Make Sure You Have An Active Internet Connection")
                                .setPositiveButton("OK") { _, _ ->
                                }
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                    }
                }
        } else {
            Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Registration Successful!")
            .setMessage("You can now take quizzes")
            .setPositiveButton("OK") { _, _ ->
                activity?.let {
                    requireView().findNavController().navigateUp()
                }
            }
            .setCancelable(false)
            .create()
            .show()

    }
}
