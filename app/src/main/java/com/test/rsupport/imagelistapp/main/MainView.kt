package com.test.rsupport.imagelistapp.main

import android.view.View

interface MainView {
    fun setMainRecyclerViewLayoutManager()
    fun setMainRecyclerViewAdapter(
            imageUrls: List<String>, onClickLoadMoreImagesButton: View.OnClickListener)
    fun notifyMainRecyclerViewAdapterDataSetChanged()
}