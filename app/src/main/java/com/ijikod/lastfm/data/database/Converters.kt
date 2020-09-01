package com.ijikod.lastfm.data.database

import androidx.room.TypeConverter
import com.ijikod.lastfm.data.model.Image
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class Converters {

    private val moshi = Moshi.Builder().build()
    private val listString : ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)

    private val listImage : ParameterizedType = Types.newParameterizedType(List::class.java, Image::class.java)
    private val listImagesJsonAdapter: JsonAdapter<List<Image>> = moshi.adapter(listImage)



    @TypeConverter
    fun listImageToJsonStr(listMash: List<Image>?): String? {
        return listImagesJsonAdapter.toJson(listMash)
    }

    @TypeConverter
    fun jsonImageToListString(jsonStr: String?): List<Image>? {
        return jsonStr?.let { listImagesJsonAdapter.fromJson(jsonStr) }
    }


}