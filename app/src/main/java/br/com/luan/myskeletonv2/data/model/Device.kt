package br.com.luan.myskeletonv2.data.model

import com.google.gson.annotations.SerializedName

class Device {

    @SerializedName("id_device")
    var idDevice: String? = null

    @SerializedName("token_device")
    var tokenDevice: String? = null

    override fun toString(): String {
        return "Device{" +
                "id_device = '" + idDevice + '\'' +
                ",token_device = '" + tokenDevice + '\'' +
                "}"
    }
}