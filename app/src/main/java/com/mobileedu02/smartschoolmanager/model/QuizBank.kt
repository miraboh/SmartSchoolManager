package com.mobileedu02.smartschoolmanager.model

import com.mobileedu02.smartschoolmanager.model.Quiz

class QuizBank(val mQuestionList: List<Quiz>?) {
    private var mNextQuestionIndex: Int = 0

    val question: Quiz
        get() {
            if (mNextQuestionIndex == mQuestionList!!.size) {
                mNextQuestionIndex = 0
            }
            return mQuestionList[mNextQuestionIndex++]
        }

    init {
        mQuestionList
        mNextQuestionIndex = 0
    }
}