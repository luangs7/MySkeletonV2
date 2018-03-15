package br.com.luan.myskeletonv2.view.ui.Push.model

import com.google.gson.annotations.SerializedName

class FirebaseNotification {

    @SerializedName("notification")
    var notification: Notification? = null

    @SerializedName("to")
    var to: String? = null

    @SerializedName("priority")
    var priority: String? = null

    override fun toString(): String {
        return "FirebaseNotification{" +
                "notification = '" + notification + '\'' +
                ",to = '" + to + '\'' +
                ",priority = '" + priority + '\'' +
                "}"
    }
}