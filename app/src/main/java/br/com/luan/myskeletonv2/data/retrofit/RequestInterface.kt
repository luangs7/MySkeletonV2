package br.com.luan.myskeletonv2.data.retrofit

import br.com.luan.myskeletonv2.data.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.HeaderMap
import retrofit2.http.GET



/**
 * Created by Luan on 17/10/17.
 */
interface RequestInterface {

    @GET("locations")
    abstract fun getMachines(): Call<List<Machine>>

    @GET("machines/{id_machine}/items")
    abstract fun getItemMachine(@Path("id_machine") id: String): Call<List<MachineItem>>

    @GET("items/{id}")
    abstract fun getItemDetails(@Path("id") id: String): Call<List<MachineItem>>

    @POST("rating")
    abstract fun setRating(@Body json: RequestBody):Call<Void>

    @POST("devices")
    abstract fun setRegister(@Body json: RequestBody): Call<Void>



}