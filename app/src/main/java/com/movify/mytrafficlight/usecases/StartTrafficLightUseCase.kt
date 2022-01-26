package com.movify.mytrafficlight.usecases

import com.movify.mytrafficlight.datasources.TrafficLightDataSource
import kotlinx.coroutines.delay

class StartTrafficLightUseCase(
    private val trafficLightDataSource: TrafficLightDataSource
) {
    suspend operator fun invoke() {
        trafficLightDataSource.apply {
            setIsActive(true)
            currentTrafficLight.let { trafficLight ->
                val currentColors = trafficLight.currentColors

                for (index in currentColors.size - 1 downTo 0) {
                    if (currentTrafficLight.isActive) {
                        currentColors[index].active = true
                        if (currentColors.lastIndex == index) {
                            currentColors.first().active = false
                        } else {
                            currentColors[index + 1].active = false
                        }
                        delay(currentColors[index].time)
                        setColors(currentColors)
                    }
                }
            }
        }
    }
}