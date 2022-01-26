package com.movify.mytrafficlight.trafficlight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.getViewModel
import androidx.compose.ui.unit.dp
import com.movify.mytrafficlight.R
import com.movify.mytrafficlight.entities.TrafficLight
import com.movify.mytrafficlight.ui.theme.composable.ButtonComposable

@Composable
fun TrafficLightScreen(carModel: String) {
    val viewModel = getViewModel<TrafficLightViewModel>()

    val shape = RoundedCornerShape(12.dp)

    val trafficLightState = viewModel.trafficLightFlow.collectAsState(initial = TrafficLight())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = carModel,
            style = MaterialTheme.typography.h3
        )

        Column(
            Modifier
                .size(200.dp, 500.dp)
                .clip(shape)
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            trafficLightState.value.currentColors.forEach { trafficLightColor ->
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(
                            colorResource(
                                if (trafficLightColor.active) {
                                    trafficLightColor.activeColorId
                                } else {
                                    trafficLightColor.inactiveColorId
                                }
                            )
                        )
                )
            }
        }

        ButtonComposable(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(
                if (trafficLightState.value.isActive) {
                    R.string.traffic_light_screen_stop_button
                } else {
                    R.string.traffic_light_screen_start_button
                }
            )
        ) {
            viewModel.startOrStopTrafficLight(!trafficLightState.value.isActive)
        }
    }
}
