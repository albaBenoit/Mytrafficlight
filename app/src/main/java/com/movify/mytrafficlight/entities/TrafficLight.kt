package com.movify.mytrafficlight.entities

import com.movify.mytrafficlight.R
import com.movify.mytrafficlight.entities.Colors.Companion.GREEN
import com.movify.mytrafficlight.entities.Colors.Companion.GREEN_TRANSITION_TIME
import com.movify.mytrafficlight.entities.Colors.Companion.ORANGE
import com.movify.mytrafficlight.entities.Colors.Companion.ORANGE_TRANSITION_TIME
import com.movify.mytrafficlight.entities.Colors.Companion.RED
import com.movify.mytrafficlight.entities.Colors.Companion.RED_TRANSITION_TIME

class TrafficLight(
    var currentColors: List<Colors> = listOf(
        Colors(RED, R.color.dark_red, R.color.red, false, RED_TRANSITION_TIME),
        Colors(ORANGE, R.color.dark_orange, R.color.orange, false, ORANGE_TRANSITION_TIME),
        Colors(GREEN, R.color.dark_green, R.color.green, false, GREEN_TRANSITION_TIME),
    ),

    var isActive: Boolean = false
) {
    constructor(previousState: TrafficLight) : this(
        previousState.currentColors,
        previousState.isActive
    )
}

data class Colors(
    val name: String,
    val inactiveColorId: Int,
    val activeColorId: Int,
    var active: Boolean,
    val time: Long
) {
    companion object {
        const val RED = "RED"
        const val GREEN = "GREEN"
        const val ORANGE = "ORANGE"
        const val RED_TRANSITION_TIME = 4000L
        const val GREEN_TRANSITION_TIME = 4000L
        const val ORANGE_TRANSITION_TIME = 1000L
    }
}