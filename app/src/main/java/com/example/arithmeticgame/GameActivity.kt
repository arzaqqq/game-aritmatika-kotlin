package com.example.arithmeticgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var timerText: TextView
    private lateinit var scoreText: TextView
    private lateinit var answerInput: EditText
    private lateinit var submitButton: Button

    private var score = 0
    private var currentQuestionIndex = 0
    private lateinit var questions: List<Question>
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Hubungkan elemen UI dengan ID di XML
        questionText = findViewById(R.id.questionText)
        timerText = findViewById(R.id.timerText)
        scoreText = findViewById(R.id.scoreText)
        answerInput = findViewById(R.id.answerInput)
        submitButton = findViewById(R.id.submitButton)

        // Ambil level dari Intent
        val level = intent.getStringExtra("LEVEL") ?: "Easy"
        questions = generateQuestions(level)

        // Atur tombol Submit untuk memproses jawaban
        submitButton.setOnClickListener {
            submitAnswer()
        }

        // Mulai permainan
        startGame(level)
    }

    private fun startGame(level: String) {
        score = 0
        currentQuestionIndex = 0

        // Atur batas waktu berdasarkan level
        val timeLimit = when (level) {
            "Easy" -> 30_000L // 30 detik
            "Medium" -> 20_000L // 20 detik
            "Hard" -> 15_000L // 15 detik
            else -> 30_000L
        }

        // Mulai timer
        timer = object : CountDownTimer(timeLimit, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerText.text = "Waktu tersisa : ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                endGame()
            }
        }
        timer.start()

        // Tampilkan pertanyaan pertama
        showNextQuestion()
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            questionText.text = question.text
            answerInput.text.clear() // Reset input jawaban
        } else {
            timer.cancel()
            endGame()
        }
    }

    private fun endGame() {
        // Berpindah ke ScoreActivity dengan skor akhir
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra("SCORE", score) // Kirim skor akhir
        startActivity(intent)
        finish() // Tutup GameActivity
    }



    private fun generateQuestions(level: String): List<Question> {
        val questions = mutableListOf<Question>()
        val range = when (level) {
            "Easy" -> 1..10
            "Medium" -> 1..50
            "Hard" -> 1..100
            else -> 1..10
        }

        repeat(5) {
            val num1 = Random.nextInt(range.first, range.last)
            val num2 = Random.nextInt(range.first, range.last)
            val operation = listOf("+", "-", "*", "/").random()
            val questionText = "$num1 $operation $num2"

            val answer = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0) num1 / num2 else 0
                else -> 0
            }

            questions.add(Question(questionText, answer))
        }
        return questions
    }

    private fun submitAnswer() {
        val userAnswer = answerInput.text.toString().toIntOrNull() // Validasi input angka
        if (userAnswer == null) {
            Toast.makeText(this, "Masukkan jawaban yang valid!", Toast.LENGTH_SHORT).show()
            return
        }

        // Periksa jawaban pengguna
        if (userAnswer == questions[currentQuestionIndex].answer) {
            score += 20 // Tambahkan 20 poin untuk jawaban benar
            Toast.makeText(this, "Jawaban benar!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Jawaban salah!", Toast.LENGTH_SHORT).show()
        }

        // Perbarui skor dan tampilkan pertanyaan berikutnya
        currentQuestionIndex++
        scoreText.text = "Score: $score"
        showNextQuestion()
    }

}

// Model data untuk soal
data class Question(val text: String, val answer: Int)
