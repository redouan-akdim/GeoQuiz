package com.akdim.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val COUNTER_RIGHT_KEY = "COUNTER_RIGHT_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex:Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY)?:0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private var counterRightAnswers:Int              // Counter of right answers
        get() = savedStateHandle.get(COUNTER_RIGHT_KEY)?:0
        set(value) = savedStateHandle.set(COUNTER_RIGHT_KEY, value)

    val currentQuestionAnswer:Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText:Int
        get() = questionBank[currentIndex].textResID

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }

    fun incRightAnswers(){
        counterRightAnswers++
    }

    fun resetRightAnswers(){
        counterRightAnswers = 0
    }

    /* Getter functions */
    fun getIndex(): Int{
        return this.currentIndex
    }

    fun getQuestionBankSize(): Int {
        return this.questionBank.size
    }

    fun getCountRightAnswers(): Int{
        return counterRightAnswers
    }


}