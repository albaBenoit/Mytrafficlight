package com.movify.mytrafficlight.usecases

import com.movify.mytrafficlight.datasources.TrafficLightDataSource
import com.movify.mytrafficlight.entities.TrafficLight
import kotlinx.coroutines.flow.Flow

class GetTrafficLightUseCase(
    private val trafficLightDataSource: TrafficLightDataSource
) {
    operator fun invoke(): Flow<TrafficLight> = trafficLightDataSource.trafficLight
}