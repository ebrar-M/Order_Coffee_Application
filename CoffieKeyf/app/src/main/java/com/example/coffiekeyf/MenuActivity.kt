package com.example.coffiekeyf

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private var lastChecked: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val rbEspresso = findViewById<RadioButton>(R.id.radioEspresso)
        val rbLatte = findViewById<RadioButton>(R.id.radioLatte)
        val rbMocha = findViewById<RadioButton>(R.id.radioMocha)

        val radioButtons = listOf(rbEspresso, rbLatte, rbMocha)

        radioButtons.forEach { radio ->
            radio.setOnClickListener {
                if (radio == lastChecked) {
                    radio.isChecked = false
                    lastChecked = null
                } else {
                    lastChecked?.isChecked = false
                    radio.isChecked = true
                    lastChecked = radio
                }
            }
        }

        // View'ları tanımla
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupKahve)
        val adetInput = findViewById<EditText>(R.id.etAdet)
        val btnOnayla = findViewById<Button>(R.id.btnOnayla)
        val btnIcedCoffee = findViewById<Button>(R.id.btnIcedCoffe) // xml'de bu id olmalı
        val tvSiparisOzeti = findViewById<TextView>(R.id.tvSiparisOzeti)

        // Iced Coffee butonu → IcedCoffeeActivity'e geçiş
        btnIcedCoffee.setOnClickListener {
            val intent = Intent(this, IcedCoffeeActivity::class.java)
            startActivity(intent)
        }

        // Sipariş Onayla
        btnOnayla.setOnClickListener {
            val adetText = adetInput.text?.toString()?.trim()

            val isEspressoChecked = findViewById<RadioButton>(R.id.radioEspresso).isChecked
            val isLatteChecked = findViewById<RadioButton>(R.id.radioLatte).isChecked
            val isMochaChecked = findViewById<RadioButton>(R.id.radioMocha).isChecked

            val seciliKahve: String? = when {
                isEspressoChecked -> "Espresso"
                isLatteChecked -> "Latte"
                isMochaChecked -> "Mocha"
                else -> null
            }

            if (seciliKahve == null || adetText.isNullOrEmpty()) {
                Toast.makeText(this, "Please select coffee and enter quantity!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val adet = try {
            adetText.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
            val siparisOzet = "$adet $seciliKahve order confirmed."

            // Mini sipariş özeti AlertDialog
            val dialogView = layoutInflater.inflate(R.layout.dialog_siparis_ozeti, null)
            val tvBaslik = dialogView.findViewById<TextView>(R.id.tvDialogBaslik)
            val tvMesaj = dialogView.findViewById<TextView>(R.id.tvDialogMesaj)
            val btnTamam = dialogView.findViewById<Button>(R.id.btnDialogTamam)

            tvMesaj.text = siparisOzet

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            btnTamam.setOnClickListener {
                dialog.dismiss()
                // 🔄 Sipariş formunu temizle
                adetInput.text.clear()
                radioGroup.clearCheck()
                rbEspresso.isChecked = false
                rbLatte.isChecked = false
                rbMocha.isChecked = false
                tvSiparisOzeti.visibility = View.GONE
            }


            // ✂️ Beyaz arka planı yok etmek için bu satırı ekle:
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()


        }
    }
}

