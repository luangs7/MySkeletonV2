package br.com.luan.myskeletonv2.view.ui.FindMovie.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Dev_Maker on 19/10/2016.
 */
open class BaseRequest{


    @SerializedName("success")
    @Expose
    var isSuccess: Boolean? = null
        get() = if (field == null) false else field

    @SerializedName("message")
    @Expose
    var message: String? = null
        get() = if (field == null) "" else field

    @SerializedName("count")
    var count: Long? = null

}
