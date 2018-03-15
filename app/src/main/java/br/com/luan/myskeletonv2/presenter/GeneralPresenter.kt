package br.com.luan.myskeletonv2.presenter

import android.app.Activity
import android.app.Dialog
import android.view.View

/**
 * Created by Luan on 06/06/17.
 */

class GeneralPresenter {

    interface ActivityPresenterImpl {
        fun onEmpty()
        fun onErrorAlert(erro: String)
        fun onAlertMessage(msg: String)
        fun onAlertDialogMessage(title: String, text: String)
        fun onAlertDialogMessageFinish(title: String, text: String, mActivity: Activity)
        fun onProgressShow()
        fun onProgressDismiss()
        fun onPhoneDispatcher(number:String)
        fun onSharedButton(text:String, subject: String)
        fun startActivity(activity: Activity)
        fun startActivityClearTask(activity: Activity)
        fun startActivity(mActivity: Activity, extra: String, extraKey: String)

    }
}
