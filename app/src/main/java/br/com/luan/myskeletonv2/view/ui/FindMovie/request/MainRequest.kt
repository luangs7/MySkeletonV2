package br.com.luan.myskeletonv2.view.ui.FindMovie.request

import br.com.luan.myskeletonv2.data.model.BaseRequest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import br.com.luan.myskeletonv2.view.ui.FindMovie.Filme


class MainRequest : BaseRequest() {


    @SerializedName("page")
    @Expose
    var page: String? = null
    @SerializedName("results")
    @Expose
    var results: List<Filme>? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: String? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: String? = null
}
