package com.test.rsupport.imagelistapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.test.rsupport.imagelistapp.R
import com.test.rsupport.imagelistapp.service.ImageListService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val imageListService = ImageListService.getInstance()

    var imageUrls = ArrayList<String>()
    var page = 1

    val onClickLoadMoreImagesButton: View.OnClickListener = View.OnClickListener {
        page++
        loadImageList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set layoutManager
        recyclerviewMain.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerviewMain.setHasFixedSize(true)

        //set adapter
        recyclerviewMain.adapter =
                ImageListAdapter(this@MainActivity, imageUrls, onClickLoadMoreImagesButton)

        loadImageList()
    }

    fun loadImageList() {
        val map = HashMap<String, String>()
        map.put("phrase", "collaboration")
        map.put("sort", "mostpopular")
        map.put("page", "$page")

        imageListService.getImageListAPI()
                .imageInfos("photos", "collaboration", map)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val responseBody = response?.body()?.string()

                        val elements = Jsoup.parse(responseBody).select("article")
                        for(element in elements) {
                            imageUrls.add(element.attr("data-thumb-url"))
                        }

                        recyclerviewMain.adapter?.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

                    }
                })
    }

    companion object {
        val TAG = "MainActivity"
    }
}
