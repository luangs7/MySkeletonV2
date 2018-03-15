package br.com.luan.myskeletonv2.data.request

import android.app.Activity
import android.content.Context
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.model.Machine
import br.com.luan.myskeletonv2.data.model.MachineItem
import br.com.luan.myskeletonv2.data.model.Resource
import br.com.luan.myskeletonv2.view.ui.Push.model.FirebaseNotification
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

class FirebaseRequestActivity(private val activity: Activity, private val mListener: RequestListener) {
    private val context: Context

    init {
        this.context = activity.baseContext
    }

    interface RequestListener {
        fun onSuccess()
        fun onError()
    }

//
    fun sendPush(notification:FirebaseNotification) {

    val header = HashMap<String,String>()
    header.put("Authorization", "key=" + context.resources.getString(R.string.firebaseAuth))

    val json = Gson().toJson(notification)
    val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)


    ApiManager(context,"https://fcm.googleapis.com/fcm/")
                .retrofit
                .create(RequestInterface::class.java)
                .sendPush(header,body)
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<Void> {
                    override fun onResponse(response: Void?) {
                        mListener.onSuccess()
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError()
                    }

                    override fun onRetry(t: Throwable?) {
                        sendPush(notification)
                    }
                },false))
    }


}
