package br.com.luan.myskeletonv2.view.ui.Push

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.request.FirebaseRequestActivity
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import br.com.luan.myskeletonv2.view.ui.Push.model.FirebaseNotification
import br.com.luan.myskeletonv2.view.ui.Push.model.Notification
import kotlinx.android.synthetic.main.activity_push.*

class PushActivity : BaseDrawerActivity(), FirebaseRequestActivity.RequestListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push)


        this.positive.setOnClickListener(View.OnClickListener { v: View? -> submit() })
    }

    fun submit(){
        var firebase = FirebaseNotification()
        var notification = Notification()
        firebase.priority = "normal"
        firebase.to = "/topics/general"
        notification.text = this.content.text.toString()
        notification.title = this.alertTitle.text.toString()

        firebase.notification = notification

        FirebaseRequestActivity(this,this).sendPush(firebase)
    }

    override fun onSuccess() {
        onAlertDialogMessage("Sucesso!","Mensagem push enviada com sucesso por topics!")
    }

    override fun onError() {
        onAlertDialogMessage("Erro!","Erro ao enviar mensagem de push!")

    }
}
