package com.test.livermor.myapplication.coordinator

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.util.AttributeSet
import android.view.View
import com.test.livermor.myapplication.R
import com.test.livermor.myapplication.coordinator.behavior.OffsetView
import com.test.livermor.myapplication.coordinator.behavior.Rule
import com.test.livermor.myapplication.coordinator.behavior.TopInfoBehavior
import com.test.livermor.myapplication.utils.getStatusBarHeight
import com.test.livermor.myapplication.utils.pixels
import kotlinx.android.synthetic.main.activity_scrolling.view.*

/**
 * @author dumchev on 12/10/2018.
 */

class TopInfoBehaviorExample(
        context: Context?,
        attrs: AttributeSet?
) : TopInfoBehavior(context, attrs) {

    override fun calcAppbarHeight(child: View): Int {
        val childHeight = child.height
        val statusBarHeight = child.getStatusBarHeight()
        return (childHeight + child.pixels(R.dimen.toolbar_height) + statusBarHeight).toInt()
    }

    override fun View.provideAppbar(): AppBarLayout = app_bar

    override fun View.setUpViews(): List<OffsetView> = listOf(
            OffsetView(
                    view = llTopDetails,
                    rules = listOf(Rule.Y(min = R.dimen.zero, max = R.dimen.toolbar_height))
            ),
            OffsetView(
                    view = ivTop,
                    rules = listOf(Rule.X(min = R.dimen.zero, max = R.dimen.mar))
            ),
            OffsetView(
                    view = tvTopDetails,
                    rules = listOf(
                            Rule.Alpha(min = 0.6f, max = 1f),
                            Rule.X(min = R.dimen.zero, max = R.dimen.mar)
                    )
            ),
            OffsetView(
                    view = tvTop,
                    rules = listOf(Rule.Appearance(appearOnValue = 0.8f))
            ),
            OffsetView(
                    view = tvTop2,
                    rules = listOf(Rule.Appearance(appearOnValue = 0.8f))
            )
    )
}