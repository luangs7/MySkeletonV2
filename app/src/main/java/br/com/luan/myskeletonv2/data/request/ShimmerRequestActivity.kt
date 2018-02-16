package br.com.luan.myskeletonv2.data.request

import android.app.Activity
import android.content.Context
import br.com.luan.myskeletonv2.data.model.Machine
import br.com.luan.myskeletonv2.data.model.MachineItem
import br.com.luan.myskeletonv2.data.model.Resource
import br.com.luan.myskeletonv2.view.ui.ShimmerList.model.Order
import br.com.squarebits.myskeletonv2.data.retrofit.ApiManager
import br.com.squarebits.myskeletonv2.data.retrofit.CustomCallback
import br.com.squarebits.myskeletonv2.data.retrofit.RequestInterface


import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.HashMap

/**
 * Created by Luan on 07/08/17.
 */

class ShimmerRequestActivity(private val activity: Activity, private val mListener: RequestListener) {
    private val context: Context

    init {
        this.context = activity.baseContext
    }

    interface RequestListener {
        fun onSuccess(orders: List<Order>?)
        fun onError(error: String)
    }

//
    fun getOrders() {


        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .getShimmerList()
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<List<Order>> {
                    override fun onResponse(response: List<Order>?) {
                        mListener.onSuccess(response!!)
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
                        getOrders()
                    }
                },false))
    }


}
