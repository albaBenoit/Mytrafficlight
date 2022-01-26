package com.movify.mytrafficlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movify.mytrafficlight.MainActivity.Companion.ROUTE_HOME
import com.movify.mytrafficlight.MainActivity.Companion.ROUTE_TRAFFIC_LIGHT
import com.movify.mytrafficlight.MainActivity.Companion.TRAFFIC_LIGHT_MODEL_ARG
import com.movify.mytrafficlight.home.HomeScreen
import com.movify.mytrafficlight.trafficlight.TrafficLightScreen
import com.movify.mytrafficlight.ui.theme.MyTrafficLightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTrafficLightTheme {
                Surface(color = MaterialTheme.colors.background) {
                }
            }

            setContent {
                val navController = rememberNavController()

                MyTrafficLightTheme {
                    Scaffold {
                        NavigationComponent(navController)
                    }
                }
            }
        }
    }

    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_TRAFFIC_LIGHT = "trafficLight"
        const val TRAFFIC_LIGHT_MODEL_ARG = "traffic_light_model_arg"
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            Screen.TrafficLight.route,
        ) { backStackEntry ->
            val carModel = backStackEntry.arguments?.getString(TRAFFIC_LIGHT_MODEL_ARG)
            TrafficLightScreen(carModel!!)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen(ROUTE_HOME)
    object TrafficLight : Screen("{$TRAFFIC_LIGHT_MODEL_ARG}/$ROUTE_TRAFFIC_LIGHT") {
        fun createRoute(modelArg: String) = "$modelArg/$ROUTE_TRAFFIC_LIGHT"
    }
}

