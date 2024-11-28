package com.example.arithmeticgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val scoreText: TextView = findViewById(R.id.scoreText)
        val btnBackToMain: Button = findViewById(R.id.btnBackToMain)

        // Ambil skor dari Intent
        val finalScore = intent.getIntExtra("SCORE", 0)
        scoreText.text = "Your Final Score: $finalScore"

        // Tombol kembali ke halaman utama
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup halaman ScoreActivity
        }
    }
}
