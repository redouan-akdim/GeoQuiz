package com.akdim.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.akdim.geoquiz.databinding.ActivityMainBinding

private const val TAG = "Mainactivity"          // Define TAG for logging
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
    private var counterRightAnswers = 0              // Counter of right answers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called")          // Send a DEBUG log message.

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /* Define event handler for clicking on the True/False Button */
        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{
            nextQuestion()

        }

        //updateQuestion()

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

        toggleBtnEnable()
    }

    /**
     * Go to the next question and update it
     */
    private fun nextQuestion(){
        currentIndex = (currentIndex + 1) % questionBank.size       // Get the next index of questionBank
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        toggleBtnEnable()

        val correctAnswer = questionBank[currentIndex].answer           // Get the correct answer of appropriate question

        // Verify user's answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        // Increment counter of right answers
        if (userAnswer == correctAnswer)
            counterRightAnswers++

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        // Compute score if last question is answered
        if (currentIndex == questionBank.size - 1){
            val formattedScore = String.format("%.1f %%",computeScore())            // Format the score accordingly

            // Display the formatted score in a Toast
            Toast.makeText(this, formattedScore, Toast.LENGTH_LONG)
                .show()

            counterRightAnswers = 0             // Reset counterRightAnswers
        }


    }

    /**
     * Function to toggle (Enable/Disable) answer buttons
     */
    private fun toggleBtnEnable(){
        binding.trueButton.isEnabled = !binding.trueButton.isEnabled
        binding.falseButton.isEnabled = !binding.falseButton.isEnabled

    }

    private fun computeScore() : Float{
        return counterRightAnswers.toFloat() / questionBank.size * 100
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}