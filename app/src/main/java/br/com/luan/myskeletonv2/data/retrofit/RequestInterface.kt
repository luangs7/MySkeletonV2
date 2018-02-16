package br.com.squarebits.myskeletonv2.data.retrofit

import br.com.luan.myskeletonv2.data.model.Product
import br.com.luan.myskeletonv2.data.model.Resource
import br.com.luan.myskeletonv2.view.ui.FindMovie.request.MainRequest
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.HeaderMap
import retrofit2.http.GET



/**
 * Created by Luan on 17/10/17.
 */
interface RequestInterface {

    @GET("einnd")
    abstract fun getProducts(): Call<Resource>

    @GET("popular")
    abstract fun getFilmesPopular(@Query("api_key") api_key: String, @Query("page") page: String): Call<MainRequest>

    @GET("top_rated")
    abstract fun getFilmesVotados(@Query("api_key") api_key: String, @Query("page") page: String): Call<MainRequest>


}