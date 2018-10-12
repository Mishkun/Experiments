package com.test.livermor.myapplication.coordinator.behavior

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.test.livermor.myapplication.utils.setHeight
import kotlinx.android.synthetic.main.activity_scrolling.view.*

/**
 * @author dumchev on 10/10/2018.
 */
abstract class TopInfoBehavior(
        context: Context?,
        attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var views: List<OffsetView> = emptyList()
    private var lastChildHeight = -1
    private val initialViewDetails: MutableMap<OffsetView, InitialViewDetails> = HashMap()

    private var needToUpdateHeight: Boolean = true

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        firstInit(child, dependency)

        val progress = calcProgress(parent)
        views.forEach { performRules(offsetView = it, percent = progress) }
        return true
    }

    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {

        val canUpdateHeight = canUpdateHeight(calcProgress(parent))
        if (canUpdateHeight) {
            parent.post {
                val newChildHeight = child.height
                if (newChildHeight != lastChildHeight) {
                    lastChildHeight = newChildHeight
                    setUpAppbarHeight(child, parent)
                }
            }
        } else {
            needToUpdateHeight = true
        }
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    protected abstract fun calcAppbarHeight(child: View): Int
    protected abstract fun View.setUpViews(): List<OffsetView>
    protected abstract fun View.provideAppbar(): AppBarLayout
    protected open fun canUpdateHeight(progress: Float): Boolean = true

    private fun calcProgress(parent: CoordinatorLayout): Float {
        val app_bar = parent.provideAppbar()
        val scrollRange = app_bar.totalScrollRange.toFloat()
        val scrollY = Math.abs(app_bar.y)
        val scroll = 1 - scrollY / scrollRange
        if (scroll.isNaN()) {
            return 1f
        }
        return scroll
    }

    private fun setUpAppbarHeight(child: View, parent: ViewGroup) {
        parent.toolbar_layout?.setHeight(calcAppbarHeight(child))
    }

    private fun firstInit(child: View, dependency: View) {
        if (needToUpdateHeight) {
            setUpAppbarHeight(child, dependency as ViewGroup)
            needToUpdateHeight = false
        }

        if (views.isEmpty()) { // todo: clear condition
            views = child.setUpViews()
            views.forEach { it ->
                initialViewDetails.put(
                        it,
                        InitialViewDetails(
                                x = it.view.x,
                                y = it.view.y,
                                alpha = it.view.alpha
                        )
                )
            }
        }
    }

    private fun performRules(offsetView: OffsetView, percent: Float) {
        val view = offsetView.view
        val details = initialViewDetails[offsetView]!!
        offsetView.rules.forEach { rule ->
            val ratio = rule.interpolator.getInterpolation(percent)
            rule.manage(ratio, details, view)
        }
    }
}
