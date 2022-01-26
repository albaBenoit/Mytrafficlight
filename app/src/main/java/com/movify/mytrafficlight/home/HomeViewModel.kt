package com.movify.mytrafficlight.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    fun isModelValid(model: String) =
        model.isNotBlank()
}