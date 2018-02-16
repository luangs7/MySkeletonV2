package br.com.luan.myskeletonv2.view.ui.BottomAlert

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.BottomDialogMessage
import br.com.luan.myskeletonv2.view.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_bottom_alert.*

class BottomAlertActivity : BaseActivity(), BottomDialogMessage.ConfirmClickListener {

    internal var size:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_alert)
        setSupportActionBar(this.toolbar)

        validateRadio();

        this.positive.setOnClickListener(View.OnClickListener { v: View? ->
            if(match.isChecked || wrap.isChecked || custom.isChecked){
                if(custom.isChecked){
                    if( customSize.text.toString().length > 0)
                        size = customSize.text.toString()
                    else
                        size = "0"
                }
                if(alertTitle.text.toString().length > 0 && content.text.toString().length > 0 ){
                    BottomDialogMessage(this,this).setDialog(alertTitle.text.toString(),content.text.toString(),size)
                }else
                    onAlertMessage("Insert a title and a message for your alert")
            }else
                onAlertMessage("Select one size")
        })
    }


    fun validateRadio(){
        this.match.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                this.wrap.isChecked = false
                this.custom.isChecked = false
                this.customSize.setText("0")
                this.customSize.isEnabled = false
                size = "match"
            }
        })

        this.wrap.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                this.match.isChecked = false
                this.custom.isChecked = false
                this.customSize.setText("0")
                this.customSize.isEnabled = false
                size = "wrap"

            }
        })


        this.custom.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                this.wrap.isChecked = false
                this.match.isChecked = false
                this.customSize.setText("0")
                this.customSize.isEnabled = true
            }
        })
    }


    override fun onConfirm() {
//        onAlertMessage("")
    }
}
