package br.com.luan.myskeletonv2.extras

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import br.com.luan.myskeletonv2.R

/**
 * Created by Luan on 26/01/18.
 */
class BottomDialogMessage(activity: Activity, clickListener: ConfirmClickListener){

    var activity:Activity? = null
    var clickListener:ConfirmClickListener? = null
    lateinit var mBottomSheetDialog: Dialog


    init{
        this.activity = activity
        this.clickListener = clickListener
    }

    public fun setDialog(title: String, text: String){
        val view = activity!!.layoutInflater.inflate(R.layout.alert_bottom, null)
        val alerttitle = view.findViewById(R.id.title) as TextView
        val content = view.findViewById(R.id.text) as TextView
        val positive = view.findViewById(R.id.positive) as Button

        content.text = text
        alerttitle.text = title

        mBottomSheetDialog = Dialog(activity, R.style.MaterialDialogSheet)

        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        positive.setOnClickListener(View.OnClickListener {
           clickListener!!.onConfirm()
        })


    }


    public fun setDialog(title: String, text: String, size:String){
        val view = activity!!.layoutInflater.inflate(R.layout.alert_bottom, null)
        val alerttitle = view.findViewById(R.id.title) as TextView
        val content = view.findViewById(R.id.text) as TextView
        val positive = view.findViewById(R.id.positive) as Button

        content.text = text
        alerttitle.text = title

        mBottomSheetDialog = Dialog(activity, R.style.MaterialDialogSheet)

        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(false)
        if(size.equals("wrap"))
            mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        else if(size.equals("match"))
            mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        else {
            if(size.toInt() > 400)
                mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, size.toInt())
            else
                mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        }

        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        positive.setOnClickListener(View.OnClickListener {
            clickListener!!.onConfirm()
            mBottomSheetDialog.dismiss()

        })


    }



    interface ConfirmClickListener{
        fun onConfirm()
    }

}