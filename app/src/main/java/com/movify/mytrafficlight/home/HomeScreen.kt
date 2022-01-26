package com.movify.mytrafficlight.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.movify.mytrafficlight.Screen
import org.koin.androidx.compose.getViewModel
import com.movify.mytrafficlight.R
import com.movify.mytrafficlight.ui.theme.composable.ButtonComposable

@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel = getViewModel<HomeViewModel>()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(id = R.string.car_model)) }
        )

        ButtonComposable(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.home_screen_validate_button)
        ) {
            if (viewModel.isModelValid(text)) {
                navController.navigate(Screen.TrafficLight.createRoute(text))
            } else {
                Toast.makeText(context, R.string.car_model_error_message, Toast.LENGTH_LONG).show()
            }
        }
    }
}