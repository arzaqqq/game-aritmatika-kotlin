



package com.example.arithmeticgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pilihan level
        val easyButton: Button = findViewById(R.id.btnEasy)
        val mediumButton: Button = findViewById(R.id.btnMedium)
        val hardButton: Button = findViewById(R.id.btnHard)

        // Pindah ke GameActivity dengan level
        easyButton.setOnClickListener { startGame("Easy") }
        mediumButton.setOnClickListener { startGame("Medium") }
        hardButton.setOnClickListener { startGame("Hard") }
    }

    private fun startGame(level: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("LEVEL", level)
        startActivity(intent)
    }
}
