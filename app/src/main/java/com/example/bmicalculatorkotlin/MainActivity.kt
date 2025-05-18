package com.example.bmicalculatorkotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.bmicalculatorkotlin.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider

class MainActivity : ComponentActivity() {

    private lateinit var ui: ActivityMainBinding
    private val vm: BmiViewModel by viewModels()         // ViewModel memegang state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        initDefaultView()
        bindListeners()
    }

    /* ---------- Inisialisasi tampilan awal ---------- */
    private fun initDefaultView() = with(ui) {
        updateGenderCard(vm.gender)
        ageText.text = vm.age.toString()
        weightText.text = vm.weight.toString()
        heightText.text = vm.height.toString()
    }

    /* ---------- Listener & Handler ---------- */
    private fun bindListeners() = with(ui) {

        /* Pilih gender */
        maleCard.setOnClickListener { selectGender(Gender.MALE) }
        femaleCard.setOnClickListener { selectGender(Gender.FEMALE) }

        /* Slider umur */
        ageSlider.addOnChangeListener { _: Slider, value, _ ->
            vm.age = value.toInt()
            ageText.text = vm.age.toString()
        }

        /* Tombol berat */
        weightIncrementBtn.setOnClickListener { adjustWeight(+1) }
        weightDecrementBtn.setOnClickListener { adjustWeight(-1) }

        /* Tombol tinggi */
        heightIncrementBtn.setOnClickListener { adjustHeight(+1) }
        heightDecrementBtn.setOnClickListener { adjustHeight(-1) }

        /* Hitung BMI */
        calculateBtn.setOnClickListener { navigateToResult() }
    }

    /* ---------- Helper ---------- */
    private fun selectGender(g: Gender) {
        vm.gender = g
        updateGenderCard(g)
    }

    private fun updateGenderCard(selected: Gender) = with(ui) {
        val active = Color.parseColor("#03AED2")
        val inactive = Color.WHITE
        maleCard.setCardBackgroundColor(if (selected == Gender.MALE) active else inactive)
        femaleCard.setCardBackgroundColor(if (selected == Gender.FEMALE) active else inactive)
    }

    private fun adjustWeight(delta: Int) = with(ui) {
        vm.weight = (vm.weight + delta).coerceAtLeast(1)
        weightText.text = vm.weight.toString()
    }

    private fun adjustHeight(delta: Int) = with(ui) {
        vm.height = (vm.height + delta).coerceAtLeast(1)
        heightText.text = vm.height.toString()
    }

    private fun navigateToResult() {
        if (vm.isInputValid()) {
            startActivity(
                Intent(this, ResultActivity::class.java).apply {
                    putExtras(vm.toBundle())
                }
            )
        } else {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_LONG).show()
        }
    }
}
