package com.example.bmicalculatorkotlin

import android.os.Bundle
import androidx.lifecycle.ViewModel

enum class Gender { MALE, FEMALE }

class BmiViewModel : ViewModel() {

    var gender: Gender = Gender.MALE
    var age: Int = 20
    var weight: Int = 50
    var height: Int = 120

    fun isInputValid(): Boolean = age > 0 && weight > 0 && height > 0

    fun toBundle() = Bundle().apply {
        putInt("age", age)
        putInt("weight", weight)
        putInt("height", height)
        putString("gender", gender.name)
    }
}
