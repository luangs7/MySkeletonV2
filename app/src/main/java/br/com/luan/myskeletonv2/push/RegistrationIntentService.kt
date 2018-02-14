package br.com.luan.myskeletonv2.push

import android.app.IntentService
import android.content.Context
import android.content.Intent
import br.com.luan.myskeletonv2.data.retrofit.ApiManager
import br.com.luan.myskeletonv2.data.retrofit.CustomCallback
import br.com.luan.myskeletonv2.extras.CustomGsonAdapter
import android.provider.Settings.Secure;
import android.util.Log
import br.com.luan.myskeletonv2.data.model.Device
import br.com.luan.myskeletonv2.data.model.MachineItem
import br.com.luan.myskeletonv2.data.retrofit.RequestInterface
import br.com.luan.myskeletonv2.extras.CpfCnpjMask.mListener

import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson

import java.util.HashMap


import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Luan on 22/05/17.
 */

class RegistrationIntentService : IntentService(LOG) {
    lateinit var context: Context
    lateinit var refreshedToken: String
    lateinit var android_id: String
//    internal var user: User? = null
    override fun onHandleIntent(intent: Intent?) {

        synchronized(LOG) {
            try {
                context = this
                FirebaseApp.initializeApp(context)

                android_id = Secure.getString(context.getContentResolver(),
                        Secure.ANDROID_ID);

                refreshedToken = FirebaseInstanceId.getInstance().token!!

                register(refreshedToken)
                Log.e("successToken","token")

            } catch (e: Exception) {
                Log.e("exception",e.message)
            }

        }
    }

    fun register(token: String) {

            val device = Device()
            device.idDevice = android_id
            device.tokenDevice = token
            val json = Gson().toJson(device)
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        ApiManager(context,false)
                .retrofit
                .create(RequestInterface::class.java)
                .setRegister(body)
                .enqueue(CustomCallback(context, object : CustomCallback.OnResponse<Void> {
                    override fun onResponse(response: Void?) {
                        Log.e("success","success")
                    }

                    override fun onFailure(t: Throwable?) {
                        Log.e("errorDevice","erro")

//                        mListener.onError(t!!.message!!)
                    }

                    override fun onRetry(t: Throwable?) {
//                        getMachines()
                    }
                }))

    }

    companion object {
        val LOG = "LOG"
    }
}

