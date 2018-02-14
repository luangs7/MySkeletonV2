package br.com.luan.myskeletonv2.view.ui


import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import br.com.luan.myskeletonv2.BuildConfig
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.presenter.GeneralPresenter

import org.jetbrains.anko.longToast
import org.jetbrains.anko.share


open class BaseActivity : AppCompatActivity(), GeneralPresenter.ActivityPresenterImpl {

    lateinit internal var mListener: GeneralPresenter.ActivityPresenterImpl
    lateinit internal var mViewProgress: View
    lateinit internal var mViewButton: View
    lateinit internal var mActivity: Activity
//    internal var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        user = Utils().getSharedAuth(this)
        showDebugDBAddressLogToast()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id = when(item.itemId){
            android.R.id.home ->  onBackPressed()
            else -> return true

        }


        return super.onOptionsItemSelected(item)

    }

    override
    fun onEmpty() {

    }
    override
    fun onErrorAlert(erro: String) {
        longToast(erro)
    }
    override
    fun onAlertMessage(msg: String) {
        longToast(msg)

    }

    override
    fun onProgressShow() {
        val progress = mViewProgress as ProgressBar?
        val button = mViewButton as RelativeLayout?
        progress!!.visibility = View.VISIBLE
        button!!.visibility = View.INVISIBLE
    }

    override
    fun onProgressDismiss() {
        val progress = mViewProgress as ProgressBar?
        val button = mViewButton as RelativeLayout?
        progress!!.visibility = View.GONE
        button!!.visibility = View.VISIBLE
    }


    override fun startActivityClearTask(activity: Activity) {
        val intent = Intent(baseContext, activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        finish()
        startActivity(intent)

    }
    override
    fun startActivity(activity: Activity) {
        startActivity(Intent(baseContext, activity.javaClass))
    }
    override
    fun startActivity(mActivity: Activity, extra: String, extraKey:String) {
//        startActivity(intentFor<MainActivity>(extraKey to extra).clearTop())

//        startActivity(Intent(baseContext, activity.javaClass).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

    }


    override
    fun onAlertDialogMessage(title: String, text: String, mActivity: Activity) {
        val builder = AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
        builder.setTitle(title)
        builder.setMessage(text)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { arg0, _ ->
            arg0.dismiss()
            finish()
        }

        val alerta = builder.create()
        alerta.show()
    }

    override
    fun onAlertDialogMessageFinish(title: String, text: String, mActivity: Activity) {
        val builder = AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(text)
        builder.setPositiveButton("OK") { arg0, _ ->
            arg0.dismiss()
            finishAffinity()
            startActivity(Intent(baseContext, mActivity.javaClass))
        }

        val alerta = builder.create()
        alerta.show()
    }

    override fun onPhoneDispatcher(number:String) {
//        makeCall(number)
    }

    override fun onSharedButton(text:String,subject: String) {
        share(text,subject)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {

        fun showDebugDBAddressLogToast() {
            if (BuildConfig.DEBUG) {
                try {
                    val debugDB = Class.forName("com.amitshekhar.DebugDB")
                    val getAddressLog = debugDB.getMethod("getAddressLog")
                    val value = getAddressLog.invoke(null)
                    Log.d("DB_DEBUG", value as String)
                    //                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
                } catch (ignore: Exception) {

                }

            }
        }
    }


}
