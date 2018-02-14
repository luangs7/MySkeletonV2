package br.com.luan.myskeletonv2.data.request

import android.app.Activity
import android.content.Context
import br.com.luan.myskeletonv2.data.model.Rating
import br.com.luan.myskeletonv2.extras.Utils
import br.com.luan.myskeletonv2.data.retrofit.ApiManager
import br.com.luan.myskeletonv2.data.retrofit.CustomCallback
import br.com.luan.myskeletonv2.data.retrofit.RequestInterface
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Luan on 07/08/17.
 */

class RatingRequestActivity(private val activity: Activity, private val mListener: RequestListener) {
    private val context: Context

    init {
        this.context = activity.baseContext
    }

    interface RequestListener {
        fun onSuccess()
        fun onError(error: String)
    }


//
    fun setRating(rating: Rating) {

        val json = Gson().toJson(rating)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .setRating(body)
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<Void> {
                    override fun onResponse(response: Void?) {
                        mListener.onSuccess()
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
                        setRating(rating)
                    }
                }))
    }



}