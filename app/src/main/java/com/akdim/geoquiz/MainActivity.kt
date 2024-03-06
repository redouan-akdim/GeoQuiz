package com.akdim.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.akdim.geoquiz.databinding.ActivityMainBinding

private const val TAG = "Mainactivity"          // Define TAG for logging
class MainActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->

        // get results
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called")          // Send a DEBUG log message.

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a  QuizViewModel:$quizViewModel")

        /* Define event handler for clicking on the True/False Button */
        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }

        binding.cheatButton.setOnClickListener{
            //val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)

            //startActivity(intent)
            cheatLauncher.launch(intent)
        }

        binding.nextButton.setOnClickListener{
            nextQuestion()

        }

        // Show current question
        val questionTextResId = quizViewModel.currentQuestionText        // Access the text resource ID of the current Question object
        binding.questionTextview.setText(questionTextResId)

        // Set onClickListener for the TextView
        binding.questionTextview.setOnClickListener{
            nextQuestion()
        }

        binding.prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        /**
         * Display the current question
         */
        val questionTextResId = quizViewModel.currentQuestionText        // Access the text resource ID of the current Question object
        binding.questionTextview.setText(questionTextResId)

        toggleBtnEnable()
    }

    /**
     * Go to the next question and update it
     */
    private fun nextQuestion(){
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        toggleBtnEnable()
        val correctAnswer = quizViewModel.currentQuestionAnswer          // Get the correct answer of appropriate question

        // Verify user's answer
       /* val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }*/
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        // Increment counter of right answers
        if (userAnswer == correctAnswer)
            quizViewModel.incRightAnswers()

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        // Compute score if last question is answered
        if (quizViewModel.getIndex() == quizViewModel.getQuestionBankSize() - 1){
            val formattedScore = String.format("%.1f %%",computeScore())            // Format the score accordingly

            // Display the formatted score in a Toast
            Toast.makeText(this, formattedScore, Toast.LENGTH_LONG)
                .show()

            quizViewModel.resetRightAnswers()
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
        return quizViewModel.getCountRightAnswers().toFloat() / quizViewModel.getQuestionBankSize() * 100
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