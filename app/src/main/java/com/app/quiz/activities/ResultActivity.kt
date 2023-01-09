package com.app.quiz.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.app.quiz.R
import com.app.quiz.models.Quiz
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login_intro.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {
    lateinit var quiz: Quiz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpViews()

        btnGoHome.setOnClickListener {
            redirect("MAIN")
        }

    }

    private fun redirect(name:String){
        val intent = when(name){
            "MAIN" -> Intent(this, MainActivity::class.java )
            else -> throw Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun addCalculateScore(score: Int) {
        var totalScore = 0
        totalScore  = score + totalScore
        txtTotalScore.text = "Your Total Score is : $totalScore"
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#2986cc'>Correct Answer: ${question.answer}</font><br/><br/>")
            if (question.answer == question.userAnswer) {
                builder.append("<font color='#009700'>Your Answer: ${question.userAnswer}</font><br/><br/>")
            } else {
                builder.append("<font color='#f44336'>Your Answer: ${question.userAnswer}</font><br/><br/>")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
            } else {
                txtAnswer.text = Html.fromHtml(builder.toString());
            }
        }
    }

    private fun calculateScore() {
        var score = 0
        for (entry in quiz.questions.entries) {
            val question = entry.value
            if (question.answer == question.userAnswer) {
                score += 10
            }
        }
        txtScore.text = "Your Score : $score"
//        addCalculateScore(score)
    }
}