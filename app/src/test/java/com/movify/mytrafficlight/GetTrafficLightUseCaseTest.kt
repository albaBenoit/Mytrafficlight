package com.movify.mytrafficlight

import com.movify.mytrafficlight.datasources.TrafficLightDataSource
import com.movify.mytrafficlight.di.myTrafficLightModule
import com.movify.mytrafficlight.entities.Colors
import com.movify.mytrafficlight.entities.TrafficLight
import com.movify.mytrafficlight.usecases.GetTrafficLightUseCase
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock

class GetTrafficLightUseCaseTest: KoinTest {

    private val getTrafficLightUseCase: GetTrafficLightUseCase by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(listOf(myTrafficLightModule))
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Test
    fun checkGetTrafficLightUseCaseRedLight() {
        declareMock<TrafficLightDataSource> {
            every {
                trafficLight
            } answers {
                flowOf(
                    TrafficLight(
                        listOf(
                            Colors(Colors.RED, R.color.dark_red, R.color.red, true, Colors.RED_TRANSITION_TIME),
                            Colors(Colors.ORANGE, R.color.dark_orange, R.color.orange, false, Colors.ORANGE_TRANSITION_TIME),
                            Colors(Colors.GREEN, R.color.dark_green, R.color.green, false, Colors.GREEN_TRANSITION_TIME),
                        ),
                        true
                    )
                )
            }

            runBlocking {
                getTrafficLightUseCase().take(1).collect {
                    check(it.isActive)
                    check(it.currentColors[0].active)
                    check(!it.currentColors[1].active)
                    check(!it.currentColors[2].active)
                }
            }
        }
    }

    @Test
    fun checkGetTrafficLightUseCaseOrangeLight() {
        declareMock<TrafficLightDataSource> {
            every {
                trafficLight
            } answers {
                flowOf(
                    TrafficLight(
                        listOf(
                            Colors(Colors.RED, R.color.dark_red, R.color.red, false, Colors.RED_TRANSITION_TIME),
                            Colors(Colors.ORANGE, R.color.dark_orange, R.color.orange, true, Colors.ORANGE_TRANSITION_TIME),
                            Colors(Colors.GREEN, R.color.dark_green, R.color.green, false, Colors.GREEN_TRANSITION_TIME),
                        ),
                        true
                    )
                )
            }

            runBlocking {
                getTrafficLightUseCase().take(1).collect {
                    check(it.isActive)
                    check(!it.currentColors[0].active)
                    check(it.currentColors[1].active)
                    check(!it.currentColors[2].active)
                }
            }
        }
    }

    @Test
    fun checkGetTrafficLightUseCaseGreenLight() {
        declareMock<TrafficLightDataSource> {
            every {
                trafficLight
            } answers {
                flowOf(
                    TrafficLight(
                        listOf(
                            Colors(Colors.RED, R.color.dark_red, R.color.red, false, Colors.RED_TRANSITION_TIME),
                            Colors(Colors.ORANGE, R.color.dark_orange, R.color.orange, false, Colors.ORANGE_TRANSITION_TIME),
                            Colors(Colors.GREEN, R.color.dark_green, R.color.green, true, Colors.GREEN_TRANSITION_TIME),
                        ),
                        true
                    )
                )
            }

            runBlocking {
                getTrafficLightUseCase().take(1).collect {
                    check(it.isActive)
                    check(!it.currentColors[0].active)
                    check(!it.currentColors[1].active)
                    check(it.currentColors[2].active)
                }
            }
        }
    }
}