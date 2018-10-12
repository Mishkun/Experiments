package com.test.livermor.myapplication.utils

import android.content.Context
import android.content.res.Resources
import android.support.annotation.DimenRes
import android.view.View
import android.widget.Toast

/**
 * @author dumchev on 25.03.2018.
 */

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT)
}

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun View.pixels(@DimenRes dimen: Int): Float =
        resources.getDimensionPixelOffset(dimen).toFloat()

fun Context.pixels(@DimenRes dimen: Int): Float =
        resources.getDimensionPixelOffset(dimen).toFloat()

fun View.setHeight(height: Int) {
    val lp = layoutParams
    lp.height = height
    layoutParams = lp
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = getResources().getDimensionPixelSize(resourceId)
    }
    return result
}

fun View.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = getResources().getDimensionPixelSize(resourceId)
    }
    return result
}
