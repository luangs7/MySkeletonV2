package br.com.luan.myskeletonv2.data.request

import android.app.Activity
import android.content.Context
import br.com.luan.myskeletonv2.data.model.Machine
import br.com.luan.myskeletonv2.data.model.MachineItem
import br.com.luan.myskeletonv2.data.model.Resource
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

class ProductRequestActivity(private val activity: Activity, private val mListener: RequestListener) {
    private val context: Context

    init {
        this.context = activity.baseContext
    }

    interface RequestListener {
        fun onSuccess(resource: Resource,isPaginator:Boolean)
        fun onError(error: String)
    }

//
    fun getProducts(page:Int, isPaginator:Boolean) {


        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .getProducts()
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<Resource> {
                    override fun onResponse(response: Resource?) {
                        mListener.onSuccess(response!!,isPaginator)
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
                        getProducts(page,isPaginator)
                    }
                }))
    }


}
