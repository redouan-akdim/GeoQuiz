package com.akdim.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.akdim.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout

class MainActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /* Define event handler for onClick to show snackbars */
        binding.trueButton.setOnClickListener{
            checkAnswer(true)

        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{
            nextQuestion()
        }

        updateQuestion()

        // Set onClickListener for the TextView
        binding.questionTextview.setOnClickListener{
            nextQuestion()
        }

        binding.prevButton.setOnClickListener{
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size       // Get the previous index of questionBank
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        /**
         * Display the current question
         */
        val questionTextResId = questionBank[currentIndex].textResID        // Access the text resource ID of the current Question object
        binding.questionTextview.setText(questionTextResId)
    }

    private fun nextQuestion(){
        /**
         * Go to the next question and update it
         */
        currentIndex = (currentIndex + 1) % questionBank.size       // Get the next index of questionBank
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer           // Get the correct answer of appropriate question

        // Verify user's answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }
}