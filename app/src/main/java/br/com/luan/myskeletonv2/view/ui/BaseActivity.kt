package br.com.luan.myskeletonv2.view.ui


import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import br.com.luan.myskeletonv2.presenter.GeneralPresenter
import br.com.luan.myskeletonv2.utils.showDebugDBAddressLogToast


open class BaseActivity : AppCompatActivity() {

    lateinit internal var mListener: GeneralPresenter.ActivityPresenterImpl
    lateinit internal var mViewProgress: View
    lateinit internal var mViewButton: View
    lateinit internal var mActivity: Activity
//    internal var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.showDebugDBAddressLogToast()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id = when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return true

        }


        return super.onOptionsItemSelected(item)

    }

}
