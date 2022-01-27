package com.movify.mytrafficlight

import com.movify.mytrafficlight.datasources.TrafficLightDataSource
import com.movify.mytrafficlight.di.myTrafficLightModule
import com.movify.mytrafficlight.usecases.StopTrafficLightUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class StopTrafficLightUseCaseTest : KoinTest {

    private val dataSource: TrafficLightDataSource by inject()
    private val stopTrafficLightUseCase: StopTrafficLightUseCase by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(listOf(myTrafficLightModule))
    }

    @Test
    fun checkStopTrafficLight() {
        runBlocking {
            check(!dataSource.currentTrafficLight.isActive)
            dataSource.setIsActive(true)
            check(dataSource.currentTrafficLight.isActive)
            stopTrafficLightUseCase()
            check(!dataSource.currentTrafficLight.isActive)
        }
    }
}