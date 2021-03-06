package com.mobileedu02.smartschoolmanager.ui.fragments.quiz

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobileedu02.smartschoolmanager.databinding.QuizFragmentBinding
import com.mobileedu02.smartschoolmanager.model.History
import com.mobileedu02.smartschoolmanager.model.Quiz
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : Fragment() {

    @Inject
    lateinit var quizviewModel: QuizViewModel

    private var txtQuestion: TextView? = null
    private var txtQuestionCount: TextView? = null
    private var txtCounter: TextView? = null
    private var radioGroup: RadioGroup? = null
    private var r1: RadioButton? = null
    private var r2: RadioButton? = null
    private var r3: RadioButton? = null
    private var r4: RadioButton? = null
    private var mSubmit: Button? = null
    private var mScore: Int = 0

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var colorStateList: ColorStateList? = null
    private var colorStateListCountDown: ColorStateList? = null
    private var countDownTimer: CountDownTimer? = null
    private var mProgressBar: ProgressDialog? = null

    private var timeLeft: Long = 0

    private var questionSize: Int = 20

    private var qCounter: Int = -1
    private var currQuestion: Quiz? = null
    private var qCountTotal: Int = 0

    private var score: Int = 0
    private var ans: Boolean = false
    private lateinit var binding: QuizFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = QuizFragmentBinding.inflate(inflater)

        // Doesn't not doing any thing
        binding.setLifecycleOwner(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        txtQuestion = binding.Question
        txtQuestionCount = binding.questionCount
        txtCounter = binding.timeCounter
        radioGroup = binding.radioGroup
        r1 = binding.radioButton1
        r2 = binding.radioButton2
        r3 = binding.radioButton3
        r4 = binding.radioButton4
        mSubmit = binding.submitButton
        mProgressBar = ProgressDialog(context)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        colorStateList = r1!!.textColors
        binding.submitButton.visibility = View.GONE
        colorStateListCountDown = txtCounter!!.textColors

        timeLeft = COUNTDOWN_TIMER

        // Once the take quiz fragment opens we want the timer to delay for the quiz to be fetched from the internet
        Handler().postDelayed(Runnable {
            startCountDown()
        }, 3000)

        // We need to implement this on backPressed for warning when a user clicks the
        // back button when the quiz is still on it wasn't working well that's why I commented it
//        requireActivity().onBackPressedDispatcher.addCallback(object :
//            OnBackPressedCallback(false) {
//            override fun handleOnBackPressed() {
//                if (isEnabled){
//                    val builder = AlertDialog.Builder(requireContext())
//
//                    builder.setTitle("Warning!")
//                        .setMessage("Do you really want to terminate quiz? ")
//                        .setPositiveButton("YES") { _, _ ->
//                            countDownTimer!!.cancel()
//                            subMitResult(mScore)
//                            // I don't really understand how this onbackpress work
//                            // If you do please leave a comment for me
//
//                        }
//                        .setNegativeButton("No"){_,_->
//
//                        }
//                        .setCancelable(false)
//                        .create()
//                        .show()
//                }
//            }
//        })

        quizviewModel.questionslist!!.observe(
            viewLifecycleOwner,
            Observer<List<Quiz>> { new ->
                if (new == null) {
                    radioGroup!!.visibility = View.GONE
                    mSubmit!!.visibility = View.GONE
                    return@Observer
                } else {
                    radioGroup!!.visibility = View.VISIBLE
                    mSubmit!!.visibility = View.VISIBLE
                    showQuestion(new)
                    mSubmit!!.setOnClickListener {
                        if (!ans) {
                            if (r1!!.isChecked || r2!!.isChecked || r3!!.isChecked || r4!!.isChecked) {
                                check()
                                showQuestion(new)
                            } else {
                                Toast.makeText(context, "Select Answer", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            })


        return binding.root
    }

    private fun showQuestion(data: List<Quiz>) {
        radioGroup!!.clearCheck()
        qCounter++
        // Try to simplify this logic
        qCountTotal = data.size + questionSize - data.size

        if (qCounter <= qCountTotal) {
            currQuestion = data.shuffled()[qCounter]
            binding.Question.text = currQuestion!!.question
            binding.radioButton1.text = currQuestion!!.A
            binding.radioButton2.text = currQuestion!!.B
            binding.radioButton3.text = currQuestion!!.C
            binding.radioButton4.text = currQuestion!!.D
            binding.questionCount.text = "Question: $qCounter / $qCountTotal"
            ans = false
            mSubmit!!.text = "Next"
        } else {
            mSubmit!!.text = "Submit"
            mSubmit!!.setOnClickListener {
                subMitResult(score)
                val timeDate = System.currentTimeMillis()
                quizviewModel.insert(History(0, timeDate, score))
            }
        }
    }

    private fun endGame() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Submitted Successfully")
            .setMessage("Quiz Score Now Available In The Result Section")
            .setPositiveButton("OK") { _, _ ->
                findNavController().navigateUp()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    fun subMitResult(score: Int) {
        countDownTimer!!.cancel()
        mProgressBar!!.setMessage("Submitting Exam...")
        mProgressBar!!.setCanceledOnTouchOutside(false)
        mProgressBar!!.show()
        countDownTimer!!.cancel()
        val user = mAuth!!.currentUser
        val userId = mAuth!!.currentUser!!.uid
        if (user != null) {
            val currentUserDb = mDatabaseReference!!.child(userId)

            /**
             We want to have a single score of the user at any point
             we can decide as well to store all users score, I think there is a firebase function for that, I guess 'push'
             */

            //val timeDate = System.currentTimeMillis()
            //quizviewModel.insert(History(timeDate, score))
            currentUserDb.child("score").removeValue()
            currentUserDb.child("score").setValue(score)

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mProgressBar!!.hide()
                        activity?.let {
                            endGame()
                        }
                    } else {
                        activity?.let {
                            requireView().findNavController().navigateUp()
                        }
                        Log.e(TAG, "sendEmailVerification", task.exception)
                    }
                }
        }
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateCountDown()
            }

            override fun onFinish() {
                timeLeft = 0
                countDownTimer!!.cancel()
                subMitResult(score)
            }
        }.start()
    }

    private fun updateCountDown() {
        val min = (timeLeft / 1000).toInt() / 60
        val sec = (timeLeft / 1000).toInt() % 60

        val timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        txtCounter!!.setText("Time: " + timeFormat)

        if (timeLeft < 10000) {
            txtCounter!!.setTextColor(Color.RED)
        } else {
            txtCounter!!.setTextColor(colorStateListCountDown)
        }
    }

    private fun check() {
        ans = true

        val radioSelected =
            binding.radioGroup.findViewById<View>(radioGroup!!.checkedRadioButtonId) as RadioButton
        val answer = radioGroup!!.indexOfChild(radioSelected) + 1

        if (answer == currQuestion!!.rightAns!!.toInt()) {
            score++

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    companion object {
        // Roughly 12minutes
        private var COUNTDOWN_TIMER: Long = 720000

    }

}