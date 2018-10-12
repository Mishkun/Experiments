package com.test.livermor.myapplication.adapter

import android.support.annotation.DrawableRes
import com.example.delegateadapter.delegate.diff.IComparableItem
import com.test.livermor.myapplication.R

/**
 * @author dumchev on 26.04.2018.
 */
data class ItemImage(@DrawableRes val image: Int = R.drawable.ic) : IComparableItem {

    override fun id(): Any = image
    override fun content(): Any = this
}