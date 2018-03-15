package br.com.luan.myskeletonv2.view.ui.Push.model

import com.google.gson.annotations.SerializedName

class Notification {

    @SerializedName("sound")
    var sound: String? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("title")
    var title: String? = null

    override fun toString(): String {
        return "Notification{" +
                "sound = '" + sound + '\'' +
                ",text = '" + text + '\'' +
                ",title = '" + title + '\'' +
                "}"
    }
}