package com.test.livermor.myapplication.coordinator.behavior

import android.view.animation.Interpolator
import com.test.livermor.myapplication.utils.transformInRange

// if you don't know, how this works, check http://inloop.github.io/interpolator/

class NormalizeInterpolator(val min: Float, val max: Float) : Interpolator {

    override fun getInterpolation(input: Float): Float = when {
        input < min -> 0f
        input >= max -> 1f
        else -> transformInRange(input, newMin = 0f, newMax = 1f, oldMin = min, oldMax = max)
    }
}

class ReverseLineaInterpolator() : Interpolator {
    override fun getInterpolation(input: Float): Float = 1f - input
}

class ThresholdInterpolator(val min: Float, val max: Float) : Interpolator {
    override fun getInterpolation(input: Float): Float = when {
        input < min -> 0f
        input >= max -> 1f
        else -> input
    }
}
