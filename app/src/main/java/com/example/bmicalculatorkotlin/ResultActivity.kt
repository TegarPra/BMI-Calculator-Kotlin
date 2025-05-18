package com.example.bmicalculatorkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculatorkotlin.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {

    private lateinit var ui: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityResultBinding.inflate(layoutInflater)
        setContentView(ui.root)

        /* Ambil data dari Intent */
        val args = intent.extras ?: return showInvalid()

        val weight = args.getInt("weight")
        val height = args.getInt("height")

        val bmi = calculateBmi(weight, height)
        showResult(bmi)

        /* Klik untuk hitung lagi */
        ui.headLayout.setOnClickListener { finish() }
        ui.checkAnotherBtn.setOnClickListener { finish() }
    }

    private fun calculateBmi(weightKg: Int, heightCm: Int): Double =
        weightKg / (heightCm / 100.0).pow(2)

    private fun showResult(bmi: Double) = with(ui) {
        val formatted = "%.2f".format(bmi)
        resultValue.text = formatted
        resultStatus.text = when {
            bmi < 18.5 -> getString(R.string.under_weight)
            bmi < 25.0 -> getString(R.string.normal_weight)
            bmi < 30.0 -> getString(R.string.over_weight)
            else       -> getString(R.string.obese)
        }
    }

    private fun showInvalid() = with(ui) {
        resultValue.text = "--"
        resultStatus.text = getString(R.string.invalid_input)
    }
}
