package io.github.aleksandersh.plannerapp.presentation.example.model

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
sealed class ScreenVm {

    data class Screen1(val title: String) : ScreenVm()

    data class Screen2(val title: String) : ScreenVm()
}