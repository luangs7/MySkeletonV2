package br.com.luan.myskeletonv2.data.request

import android.app.Activity
import android.content.Context
import br.com.luan.myskeletonv2.data.model.Machine
import br.com.luan.myskeletonv2.data.model.MachineItem

import br.com.luan.myskeletonv2.extras.Utils
import br.com.luan.myskeletonv2.data.retrofit.ApiManager
import br.com.luan.myskeletonv2.data.retrofit.CustomCallback
import br.com.luan.myskeletonv2.data.retrofit.RequestInterface
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.HashMap

/**
 * Created by Luan on 07/08/17.
 */

class DataRequestActivity(private val activity: Activity, private val mListener: RequestListener) {
    private val context: Context

    init {
        this.context = activity.baseContext
    }

    interface RequestListener {
        fun onSuccess(machines: List<Machine>)
        fun onSuccessItens(itens: List<MachineItem>?)
        fun onSuccessDetails(itens: MachineItem)
        fun onError(error: String)
    }

//
    fun getMachines() {


        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .getMachines()
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<List<Machine>> {
                    override fun onResponse(response: List<Machine>?) {
                        mListener.onSuccess(response!!)
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
                        getMachines()
                    }
                }))
    }

    fun getItensMachine(id:String) {
        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .getItemMachine(id)
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<List<MachineItem>> {
                    override fun onResponse(response: List<MachineItem>?) {
                        mListener.onSuccessItens(response)
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
                        getItensMachine(id)
                    }
                },false))
    }

    fun getItemDetails(id:String) {
        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .getItemDetails(id)
                .enqueue(CustomCallback(activity, object : CustomCallback.OnResponse<List<MachineItem>> {
                    override fun onResponse(response: List<MachineItem>?) {
                        mListener.onSuccessDetails(response?.get(0)!!)
                    }

                    override fun onFailure(t: Throwable?) {
                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
                        getItemDetails(id)
                    }
                },false))
    }
}
