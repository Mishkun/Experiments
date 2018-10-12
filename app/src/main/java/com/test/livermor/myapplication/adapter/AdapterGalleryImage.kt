package com.test.livermor.myapplication.adapter

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.test.livermor.myapplication.R
import kotlinx.android.synthetic.main.item_gallery_image.*
import kotlinx.android.synthetic.main.item_gallery_image.view.*

/**
 * @author dumchev on 26.04.2018.
 */
class AdapterGalleryImage : KDelegateAdapter<ItemImage>() {

    override fun onCreated(view: View) {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val padding = view.resources.getDimensionPixelOffset(R.dimen.default_side_margin)

        view.rlGalleryRoot.run {
            val lp = layoutParams as RecyclerView.LayoutParams
            lp.width = calcItemWidth(width, padding)
            layoutParams = lp
        }
    }

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