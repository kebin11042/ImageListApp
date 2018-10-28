package com.test.rsupport.imagelistapp.main

import android.view.View
import com.test.rsupport.imagelistapp.service.ImageListService
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val mainView: MainView) {

    private val imageListService: ImageListService

    private val imageUrls: ArrayList<String>
    private var page: Int

    private val onClickLoadMoreImagesButton: View.OnClickListener
    //중복 데이터 불러오기를 방지하기 위한 변수
    private var isEnableClick: Boolean

    init {
        imageListService = ImageListService.getInstance()

        imageUrls = ArrayList<String>()
        page = 1

        isEnableClick = true
        onClickLoadMoreImagesButton = View.OnClickListener {
            if(isEnableClick) loadImageList()
        }
    }

    //recyclerView 초기화, 최초 데이터 요청
    fun onCreate() {
        mainView.setMainRecyclerViewLayoutManager()
        mainView.setMainRecyclerViewAdapter(this.imageUrls, this.onClickLoadMoreImagesButton)
        loadImageList()
    }

    fun loadImageList() {
        val map = HashMap<String, String>()
        map.put("phrase", "collaboration")
        map.put("sort", "mostpopular")
        map.put("mediatype", "photography")
        map.put("page", "$page")

        //데이터를 불러 오는 동안 중복 클릭 방지를 위해 isEnableClick -> false
        isEnableClick = false
        imageListService.getImageListAPI()
                .imageInfos("photos", "collaboration", map)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                            call: Call<ResponseBody>?,
                            response: Response<ResponseBody>?) {
                        val responseCode = response?.code()

                        if(responseCode == 200) {
                            val responseBody = response.body()?.string()
                            //Jsoup을 이용하여 모든 article 태그를 불러온다
                            //article 태그의 data-thumb-url 속성의 image url을 저장
                            val elements =
                                    Jsoup.parse(responseBody).select("article")
                            for(element in elements) {
                                imageUrls.add(element.attr("data-thumb-url"))
                            }

                            mainView.notifyMainRecyclerViewAdapterDataSetChanged()
                            page++
                        } else {
                            //TODO: 응답 코드 오류에 대한 처리
                        }
                        isEnableClick = true
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        //TODO: 연결 실패에 대한 처리
                        isEnableClick = true
                    }
                })
    }


}