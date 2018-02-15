package br.com.squarebits.myskeletonv2.data.retrofit

import android.content.Context
import br.com.luan.myskeletonv2.extras.CollectionEmptySerializer
import br.com.luan.myskeletonv2.extras.UnitConverterFactory

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.Gson


/**
 * Created by Luan on 17/10/17.
 */
class ApiManager {

    var endpoint = "https://api.myjson.com/bins/"
    lateinit var context : Context
    var retrofit: Retrofit
    var okHttpClient: OkHttpClient


    constructor(context: Context, newEndpoint: String){
        this.context = context

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY


        okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                .readTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .build()




        val gson = GsonBuilder()
                .create()


        retrofit = Retrofit.Builder()
                .baseUrl(newEndpoint)
                .client(okHttpClient)
                .addConverterFactory(UnitConverterFactory)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

    }

    constructor(context: Context, isCollectionEmpty: Boolean){

            this.context = context

            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY


            okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                    .readTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                    .writeTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                    .addInterceptor(logInterceptor)
                    .build()

            retrofit = Retrofit.Builder()
                    .baseUrl(endpoint)
                    .client(okHttpClient)
                    .addConverterFactory(UnitConverterFactory)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(initGson(isCollectionEmpty)))
                    .build()

    }

    constructor(retrofit: Retrofit, okHttpClient: OkHttpClient){
        this.retrofit = retrofit
        this.okHttpClient = okHttpClient
    }



    constructor(context: Context,retrofit: Retrofit, okHttpClient: OkHttpClient){
        this.context = context
        this.retrofit = retrofit
        this.okHttpClient = okHttpClient
    }

    fun initGson(isCollectionEmpty: Boolean): Gson{
        var gson: Gson

        if(isCollectionEmpty)
            gson = GsonBuilder().registerTypeHierarchyAdapter(List::class.java, CollectionEmptySerializer()).create()
        else
            gson = GsonBuilder().create()

        return gson
    }

}