package com.akdim.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.akdim.geoquiz.databinding.ActivityCheatBinding
import com.akdim.geoquiz.databinding.ActivityMainBinding

const val EXTRA_ANSWER_SHOWN = "com.akdim.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.akdim.geoquiz.answer_is_true"
class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel: CheatViewModel by viewModels()
    //private var answerIsTrue = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cheatViewModel.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener{
            cheatViewModel.showAnswerButtonIsClicked = true         // Save state of clicked button
            updateAnswer()
        }

        if (cheatViewModel.showAnswerButtonIsClicked)
            updateAnswer()

    }

    private fun updateAnswer(){
        val answerText = when {
            cheatViewModel.answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }

        binding.answerTextView.setText(answerText)
        setAnswerShownResult(true)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        //val data = cheatViewModel.setAnswerShownResult(cheatViewModel.isAnswerShown)
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)

        //cheatViewModel.showAnswerButtonIsClicked = false
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}