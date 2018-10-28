package com.test.rsupport.imagelistapp.util

import android.graphics.Bitmap
import com.squareup.picasso.Transformation

/**
 *  이미지 비율에 따른 크기 조정
 *
 *  @param imageWidth 한개 아이템의 이미지 가로 크기 -> 화면의 1/3 크기로 고정
 */
class ImageRatioTransformation(val imageWidth : Double) : Transformation {
    override fun key(): String = "key"

    override fun transform(source: Bitmap?): Bitmap {
        val result = Bitmap.createScaledBitmap(source!!,
                imageWidth.toInt(),
                ((source.height * imageWidth) / source.width).toInt(),
                true
        )

        if(result != source) source.recycle()

        return result
    }
}