package com.test.livermor.myapplication.adapter

import com.example.delegateadapter.delegate.KDelegateAdapter
import com.test.livermor.myapplication.R
import kotlinx.android.synthetic.main.item_text.*

/**
 * @author dumchev on 13.08.2018.
 */
class AdapterText : KDelegateAdapter<ItemText>() {
    override fun getLayoutId(): Int = R.layout.item_text

    override fun isForViewType(p0: List<*>, p1: Int): Boolean = p0[p1] is ItemText

    override fun onBind(item: ItemText, viewHolder: KViewHolder) = with(viewHolder) {
        tvTitle.text = item.title
        tvDescription.text = item.description
    }
}