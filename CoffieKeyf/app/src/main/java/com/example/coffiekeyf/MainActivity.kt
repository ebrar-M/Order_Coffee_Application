package com.example.coffiekeyf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etKullaniciAdi = findViewById<EditText>(R.id.editTextUsername)
        val etSifre = findViewById<EditText>(R.id.editTextPassword)
        val btnGiris = findViewById<Button>(R.id.btnLogin)


        btnGiris.setOnClickListener {
            val kullaniciAdi = etKullaniciAdi.text.toString().trim()
            val sifre = etSifre.text.toString().trim()

            if (kullaniciAdi.isEmpty() || sifre.isEmpty()) {
                Toast.makeText(this, "Please enter your username and password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Giriş geçerli → MenuActivity'ye geç
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}
