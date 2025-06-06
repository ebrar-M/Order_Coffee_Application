package com.example.coffiekeyf

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class IcedCoffeeActivity : AppCompatActivity() {
    private var lastChecked: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iced_coffee)


        val rbIceCoffee = findViewById<RadioButton>(R.id.radioIceCoffee)
        val rbIceMocha = findViewById<RadioButton>(R.id.radioIceMocha)
        val rbBubbleTea = findViewById<RadioButton>(R.id.radioBubbleTea)

        val radioButtons = listOf(rbIceCoffee, rbIceMocha, rbBubbleTea)

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


        val adetInput = findViewById<EditText>(R.id.etAdet)
        val btnOnayla = findViewById<Button>(R.id.btnOnayla)
        val tvSiparisOzeti = findViewById<TextView>(R.id.tvSiparisOzeti)
        val btnHotCoffee = findViewById<Button>(R.id.btnCoffee)

        // 🔁 Geri dön: Hot Coffee menüsüne
        btnHotCoffee.setOnClickListener {
            finish() // Bu aktiviteyi kapatır ve geri döner
        }

        btnOnayla.setOnClickListener {
            val adetText = adetInput.text?.toString()?.trim()

            val isIcedCoffeeChecked = findViewById<RadioButton>(R.id.radioIceCoffee).isChecked
            val isIceCoffeeChecked = findViewById<RadioButton>(R.id.radioIceMocha).isChecked
            val isBubbleTeaChecked = findViewById<RadioButton>(R.id.radioBubbleTea).isChecked

            val seciliKahve: String? = when {
                isIcedCoffeeChecked -> "Iced Coffee"
                isIceCoffeeChecked -> "Ice Mocha"
                isBubbleTeaChecked -> "Bubble Tea"
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

            tvBaslik.text = "Order Summary"
            tvMesaj.text = siparisOzet

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            btnTamam.setOnClickListener {
                dialog.dismiss()


                // 🔄 Sipariş formunu temizle
                adetInput.text.clear()
                findViewById<RadioButton>(R.id.radioIceCoffee).isChecked = false
                findViewById<RadioButton>(R.id.radioIceMocha).isChecked = false
                findViewById<RadioButton>(R.id.radioBubbleTea).isChecked = false
                tvSiparisOzeti.visibility = View.GONE
            }
            // ✂️ Beyaz arka planı yok etmek için bu satırı ekle:
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            dialog.show()

        }
    }
}

