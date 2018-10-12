package com.test.livermor.myapplication.adapter

import com.example.delegateadapter.delegate.KDelegateAdapter
import com.test.livermor.myapplication.R
import kotlinx.android.synthetic.main.item_gallery_image.*

/**
 * @author dumchev on 02.07.2018.
 */
class AdapterImage : KDelegateAdapter<ItemImage>() {

    override fun onBind(item: ItemImage, viewHolder: KViewHolder) {
        viewHolder.ivImage.setImageResource(item.image)
    }

    override fun getLayoutId(): Int = R.layout.item_gallery_image

    override fun isForViewType(p0: MutableList<*>, p1: Int): Boolean {
        return p0[p1] is ItemImage
    }

    private fun calcItemWidth(screenWidth: Int, sidePadding: Int): Int =
            (screenWidth - sidePadding * 2.5).toInt() / 2
}
