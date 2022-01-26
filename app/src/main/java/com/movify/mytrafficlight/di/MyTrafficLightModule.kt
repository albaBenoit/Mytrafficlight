package com.movify.mytrafficlight.di

import com.movify.mytrafficlight.datasources.TrafficLightDataSource
import com.movify.mytrafficlight.home.HomeViewModel
import com.movify.mytrafficlight.trafficlight.TrafficLightViewModel
import com.movify.mytrafficlight.usecases.GetTrafficLightUseCase
import com.movify.mytrafficlight.usecases.StartTrafficLightUseCase
import com.movify.mytrafficlight.usecases.StopTrafficLightUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myTrafficLightModule
    get() = module {

        viewModel {
            HomeViewModel()
        }

        viewModel {
            TrafficLightViewModel(get(), get(), get())
        }

        single {
            TrafficLightDataSource()
        }

        single {
            StartTrafficLightUseCase(get())
        }

        single {
            GetTrafficLightUseCase(get())
        }

        single {
            StopTrafficLightUseCase(get())
        }
    }