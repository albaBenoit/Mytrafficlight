package com.movify.mytrafficlight.datasources

import com.movify.mytrafficlight.entities.Colors
import com.movify.mytrafficlight.entities.TrafficLight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TrafficLightDataSource {

    private val trafficLightFlow: MutableStateFlow<TrafficLight> = MutableStateFlow(TrafficLight())

    val trafficLight: Flow<TrafficLight>
        get() = trafficLightFlow

    val currentTrafficLight: TrafficLight
        get() = trafficLightFlow.value

    fun setColors(colors: List<Colors>) {
        trafficLightFlow.value = trafficLightFlow.value.let {
            TrafficLight(it).apply {
                this.currentColors = colors
            }
        }
    }

    fun setIsActive(isActive: Boolean) {
        trafficLightFlow.value = trafficLightFlow.value.let {
            TrafficLight(it).apply {
                this.isActive = isActive
                if (!isActive) {
                    currentColors.forEach { color ->
                        color .active = false
                    }
                }
            }
        }
    }
}