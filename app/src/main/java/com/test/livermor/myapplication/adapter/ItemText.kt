package com.test.livermor.myapplication.adapter

import com.example.delegateadapter.delegate.diff.IComparableItem

/**
 * @author dumchev on 13.08.2018.
 */
data class ItemText(
        val title: String,
        val description: String,
        val someLongArgument: String
) : IComparableItem {

    override fun id(): Any = title
    override fun content(): Any = this
}