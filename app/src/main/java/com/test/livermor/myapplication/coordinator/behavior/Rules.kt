package com.test.livermor.myapplication.coordinator.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.annotation.DimenRes
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.test.livermor.myapplication.utils.pixels
import com.test.livermor.myapplication.utils.transformInRange

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

    class Scale(
            override val min: Float,
            override val max: Float,
            override val interpolator: Interpolator = LinearInterpolator()
    ) : Rule() {

        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val size = transformInRange(
                    oldValue = ratio,
                    newMax = max,
                    newMin = min
            )
            // (this as TextView).setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            this.setScale(size)
        }

        private fun View.setScale(scale: Float) {
            scaleX = scale
            scaleY = scale
            pivotX = 0f
            pivotY = 0f
        }
    }

    class Alpha(
            override val min: Float,
            override val max: Float,
            override val interpolator: Interpolator = LinearInterpolator()
    ) : Rule() {
        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val alpha = transformInRange(oldValue = ratio, newMin = min, newMax = max)
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
            this.x = details.x + x
        }
    }

    class Appearance(
            appearOnValue: Float,
            reverse: Boolean = false
    ) : Rule() {
        override val min: Float = appearOnValue
        override val max: Float = appearOnValue
        override val interpolator: Interpolator = ThresholdInterpolator(min = min, max = max).run {
            if (reverse) ReverseInterpolator(this) else this
        }

        override fun manage(ratio: Float, details: InitialViewDetails, view: View) = with(view) {
            val shouldAppear = ratio != 0f
            animateAppearance(shouldAppear)
        }

    }

    protected fun View.animateAppearance(isVisible: Boolean) {
        clearAnimation()
        val alpha = if (isVisible) 1f else 0f
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

private const val ANIMATION_DURATION = 0L
