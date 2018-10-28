package com.test.rsupport.imagelistapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.test.rsupport.imagelistapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView{

    val mainPresenter = MainPresenter(this)

    companion object {
        val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.onCreate()
    }

    override fun setMainRecyclerViewLayoutManager() {
        //set layoutManager
        recyclerviewMain.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerviewMain.setHasFixedSize(true)
    }

    override fun setMainRecyclerViewAdapter(
            imageUrls: List<String>,
            onClickLoadMoreImagesButton: View.OnClickListener) {

        if(recyclerviewMain.adapter == null) {
            recyclerviewMain.adapter =
                    ImageListAdapter(this@MainActivity, imageUrls, onClickLoadMoreImagesButton)
        }
        recyclerviewMain.adapter?.notifyDataSetChanged()
    }

    override fun notifyMainRecyclerViewAdapterDataSetChanged() {
        recyclerviewMain.adapter?.notifyDataSetChanged()
    }
}
