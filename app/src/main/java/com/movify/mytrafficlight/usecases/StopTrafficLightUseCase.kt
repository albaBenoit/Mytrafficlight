package com.movify.mytrafficlight.usecases

import com.movify.mytrafficlight.datasources.TrafficLightDataSource

class StopTrafficLightUseCase(
    private val trafficLightDataSource: TrafficLightDataSource
) {
    operator fun invoke() {
        trafficLightDataSource.setIsActive(false)
    }
}