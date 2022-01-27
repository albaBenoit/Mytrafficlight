package com.movify.mytrafficlight

import com.movify.mytrafficlight.di.myTrafficLightModule
import com.movify.mytrafficlight.entities.Colors
import com.movify.mytrafficlight.usecases.GetTrafficLightUseCase
import com.movify.mytrafficlight.usecases.StartTrafficLightUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take
import org.junit.Test
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class StartTrafficLightUseCaseTest : KoinTest {

    private val startTrafficLightUseCase: StartTrafficLightUseCase by inject()
    private val getTrafficLightUseCase: GetTrafficLightUseCase by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(listOf(myTrafficLightModule))
    }

    @Test
    fun checkFirstColorIterationTrafficLight() {
        runBlocking {
            launch(Dispatchers.Default) {
                startTrafficLightUseCase()
            }

            getTrafficLightUseCase().take(1).collect {
                check(!it.currentColors[0].active)
                check(!it.currentColors[1].active)
                check(it.currentColors[2].active) // Green is activated
                check(it.currentColors[2].name == Colors.GREEN)
            }
        }
    }

    @Test
    fun checkSecondColorIterationTrafficLight() {
        runBlocking {
            launch(Dispatchers.Default) {
                startTrafficLightUseCase()
            }
            val timeBefore = System.currentTimeMillis()
            getTrafficLightUseCase().drop(1).take(1).collect {
                check(!it.currentColors[0].active)
                check(it.currentColors[1].active) // Orange is activated
                check(it.currentColors[1].name == Colors.ORANGE)
                check(!it.currentColors[2].active)

                val timeAfter = System.currentTimeMillis()

                // Check that the transition from green to orange took 4sec
                ((timeAfter - timeBefore) / 1000).toFloat().apply {
                    check(
                        "%.0f".format(this).toInt() == (Colors.GREEN_TRANSITION_TIME / 1000).toInt()
                    )
                }
            }
        }
    }

    @Test
    fun checkThirdColorIterationTrafficLight() {
        runBlocking {
            launch(Dispatchers.Default) {
                startTrafficLightUseCase()
            }
            val timeBefore = System.currentTimeMillis()
            getTrafficLightUseCase().drop(2).take(1).collect {
                check(it.currentColors[0].active) // Red is activated
                check(it.currentColors[0].name == Colors.RED)
                check(!it.currentColors[1].active)
                check(!it.currentColors[2].active)

                val timeAfter = System.currentTimeMillis()

                // Check that the transition from orange to red took 1sec
                ((timeAfter - timeBefore - Colors.GREEN_TRANSITION_TIME) / 1000).toFloat().apply {
                    check(
                        "%.0f".format(this)
                            .toInt() == (Colors.ORANGE_TRANSITION_TIME / 1000).toInt()
                    )
                }
            }
        }
    }

    @Test
    fun checkFourthColorIterationTrafficLight() {
        runBlocking {
            launch(Dispatchers.Default) {
                startTrafficLightUseCase()
            }
            val timeBefore = System.currentTimeMillis()
            getTrafficLightUseCase().drop(3).take(1).collect {
                val timeAfter = System.currentTimeMillis()

                // Check that the transition from red to green took 4sec
                ((timeAfter - timeBefore - Colors.GREEN_TRANSITION_TIME - Colors.ORANGE_TRANSITION_TIME) / 1000).toFloat()
                    .apply {
                        check(
                            "%.0f".format(this)
                                .toInt() == (Colors.RED_TRANSITION_TIME / 1000).toInt()
                        )
                    }
            }
        }
    }
}