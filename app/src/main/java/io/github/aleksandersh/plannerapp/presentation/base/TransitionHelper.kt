package io.github.aleksandersh.plannerapp.presentation.base

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat

/**
 * Created on 12.05.2019.
 * @author AleksanderSh
 */
object TransitionHelper {

    fun fadeIn(): TransitionFactory = { view ->
        view.alpha = 0f
        view.visibility = View.VISIBLE
        ViewCompat.animate(view)
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .alpha(1f)
    }

    fun fadeOut(): TransitionFactory = { view ->
        ViewCompat.animate(view)
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator())
            .alpha(0f)
    }
}