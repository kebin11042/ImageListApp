package com.test.rsupport.imagelistapp.main

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.test.rsupport.imagelistapp.R
import com.test.rsupport.imagelistapp.util.ImageRatioTransformation

/**
 * @param context
 * @param imageURLs gettyimages image url 리스트
 * @param onClickLoadMoreImagesButton Load More Images 버튼 click listener
 */
class ImageListAdapter(
        private val context: Context,
        private val imageURLs: List<String>,
        private val onClickLoadMoreImagesButton: View.OnClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageViewWidth : Double
    init {
        //한개의 imageView의 width 계산, 왼쪽 오른쪽 각각 8dp만큼 margin 고려
        imageViewWidth =
                (this.context.resources.displayMetrics.widthPixels / 3.0) -
                (2.0 * (this.context.resources.displayMetrics.density * 8.0))
    }

    /**
     *  이미지를 보여주는 일반적인 item view
     */
    inner class NormalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imageView: ImageView

        init {
            imageView = v.findViewById(R.id.imageviewList)
        }
    }

    /**
     *   리스트 가장 아래에 있는 Load More Images 버튼이 있는 item view
     */
    inner class FooterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val buttonLoadMore: AppCompatButton

        init {
            buttonLoadMore = v.findViewById(R.id.buttonLoadMore)
            buttonLoadMore.setOnClickListener(onClickLoadMoreImagesButton)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //viewType에 따른 ViewHolder를 리턴
        return if (viewType == VIEW_TYPE_NORMAL) {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_imagelist, viewGroup, false)
            NormalViewHolder(v)
        }
        else {
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_load_more, viewGroup, false)
            FooterViewHolder(v)
        }
    }

    /**
     *  가장 마지막에 있는 position에 "Load More Images" Button을 넣기 위해 VIEW_TYPE 리턴
     */
    override fun getItemViewType(position: Int): Int {
        if(position == imageURLs.size) {
            return VIEW_TYPE_FOOTER
        }
        return VIEW_TYPE_NORMAL
    }

    /**
     *  "Load More Images" Button을 추가하기 위해 imageURLs.size 보다 1만큼 증가
     */
    override fun getItemCount() = imageURLs.size + 1

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if(viewHolder is NormalViewHolder) {
            val imageUrl = imageURLs[position]
            Picasso.get()
                    .load(imageUrl)
                    .transform(ImageRatioTransformation(imageViewWidth))
                    .into(viewHolder.imageView)
        }
        else if(viewHolder is FooterViewHolder) {
            val layoutParams
                    = viewHolder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        }
    }

    companion object {
        val TAG = "ImageListAdapter"
        val VIEW_TYPE_NORMAL = 1
        val VIEW_TYPE_FOOTER = 2
    }
}