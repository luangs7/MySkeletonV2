package br.com.luan.myskeletonv2.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Luan on 09/01/18.
 */

class Resource(
        @SerializedName("products")
        @Expose
        val products: List<Product>? = null,
        @SerializedName("promotions")
        @Expose
        val promotions: List<Product>? = null,
        @SerializedName("news")
        @Expose
        val news: List<Product>? = null

)




