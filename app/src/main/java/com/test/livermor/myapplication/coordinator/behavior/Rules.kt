package com.test.livermor.myapplication.coordinator.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.annotation.DimenRes
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.test.livermor.myapplication.utils.pixels

/**
 * @author dumchev on 12/10/2018.
 */

class OffsetView(
        val view: View,
        val rules: List<Rule>
)

class InitialViewDetails(
        val x: Float,
        val y: Float,
        val alpha: Float
)

abstract class Rule {
    abstract val min: Number
    abstract val max: Number
    abstract val interpolator: Interpolator

    init {
        if (min.toInt() < 0 || max.toInt() > 1 || min.toInt() > max.toInt()) {
            throw IllegalStateException("min and max should be in range [0, 1] and min < max")
        }
    }

    abstract fun manage(ratio: Float, details: InitialViewDetails, view: View)

    class TextSize(
            @DimenRes override val min: Int,
            @DimenRes override val max: Int,
            override val interpolator: Interpolator = LinearInterpolator()
    ) : Rule() {

        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val size = transformInRange(
                    oldValue = ratio,
                    newMax = pixels(max),
                    newMin = pixels(min)
            )
            (this as TextView).setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }

    class Alpha(
            override val min: Float,
            override val max: Float,
            override val interpolator: Interpolator = LinearInterpolator()
    ) : Rule() {
        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val alpha = transformInRange(oldValue = ratio, newMin = min, newMax = max)
            Log.w("TopInfoBehavior", "performRules: alpha = $alpha")
            this.alpha = alpha
        }
    }

    class Y(
            @DimenRes override val min: Int,
            @DimenRes override val max: Int,
            override val interpolator: Interpolator = LinearInterpolator()
    ) : Rule() {
        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val y = transformInRange(
                    oldValue = ratio,
                    newMin = pixels(min),
                    newMax = pixels(max)
            )
            this.y = y + details.y
        }
    }

    class X(
            @DimenRes override val min: Int,
            @DimenRes override val max: Int,
            override val interpolator: Interpolator = LinearInterpolator()
    ) : Rule() {
        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val x = transformInRange(
                    oldValue = ratio,
                    newMin = pixels(min),
                    newMax = pixels(max)
            )
            Log.w("TopInfoBehavior", "performRules: x = $x")
            this.x = details.x + x
        }
    }

    class Appearance(appearOnValue: Float) : Rule() {
        override val min: Float = appearOnValue
        override val max: Float = appearOnValue
        override val interpolator: Interpolator = ThresholdInterpolator(min = min, max = max)

        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val shouldAppear = ratio != 0f
            Log.w("TopInfoBehavior", "performRules: shouldAppear = $shouldAppear")
            animateAppearance(shouldAppear)
        }

        private fun View.animateAppearance(isVisible: Boolean) {
            clearAnimation()
            val alpha = if (isVisible) 1f else 0f
            if (this.alpha == alpha) {
                Log.w("TopInfoBehavior", "animateAppearance: view.alpha = ${this.alpha}, alpha ${alpha}")
                return
            }
            animate().alpha(alpha).setDuration(ANIMATION_DURATION).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    if (isVisible) isEnabled = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (isVisible.not()) isEnabled = false
                }
            })
        }
    }
}

private const val ANIMATION_DURATION = 0L
