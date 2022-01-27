package com.movify.mytrafficlight.trafficlight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movify.mytrafficlight.entities.TrafficLight
import com.movify.mytrafficlight.usecases.GetTrafficLightUseCase
import com.movify.mytrafficlight.usecases.StartTrafficLightUseCase
import com.movify.mytrafficlight.usecases.StopTrafficLightUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrafficLightViewModel(
    private val startTrafficLightUseCase: StartTrafficLightUseCase,
    private val stopTrafficLightUseCase: StopTrafficLightUseCase,
    private val getTrafficLightUseCase: GetTrafficLightUseCase
) : ViewModel() {
    var job: Job? = null
    val trafficLightFlow: Flow<TrafficLight> = getTrafficLightUseCase()

    init {
        startTrafficLight()
    }

    fun startOrStopTrafficLight(start: Boolean) {
        if (start) {
            startTrafficLight()
        } else {
            stopTrafficLightUseCase()
            job!!.cancel()
            job = null
        }
    }

    private fun startTrafficLight() {
        job = viewModelScope.launch(Dispatchers.Default) {
            getTrafficLightUseCase().collect {
                do {
                    startTrafficLightUseCase()
                } while (it.isActive)
            }
        }
    }
}