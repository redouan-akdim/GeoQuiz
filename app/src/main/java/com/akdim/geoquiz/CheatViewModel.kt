package com.akdim.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val ANSWER_IS_TRUE_KEY = "ANSWER_IS_TRUE_KEY"
const val SHOW_ANSWER_BUTTON_IS_CLICKED_KEY = "SHOW_ANSWER_BUTTON_IS_CLICKED_KEY"

class CheatViewModel (private val savedStateHandle: SavedStateHandle): ViewModel()  {

    var showAnswerButtonIsClicked
        get() = savedStateHandle.get(SHOW_ANSWER_BUTTON_IS_CLICKED_KEY)?:false
        set(value) = savedStateHandle.set(SHOW_ANSWER_BUTTON_IS_CLICKED_KEY, value)

    var answerIsTrue
        get() = savedStateHandle.get(ANSWER_IS_TRUE_KEY)?:false
        set(value) = savedStateHandle.set(ANSWER_IS_TRUE_KEY, value)

    /*var isAnswerShown
        get() = savedStateHandle.get(EXTRA_ANSWER_SHOWN)?:false
        set(value) = savedStateHandle.set(EXTRA_ANSWER_SHOWN, value)*/


}