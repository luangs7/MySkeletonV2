package br.com.luan.myskeletonv2.data.model

import com.google.gson.annotations.SerializedName

class MachineItem {

    @SerializedName("name_item")
    var nameItem: String? = null

    @SerializedName("photos_item")
    var photosItem: List<String>? = null

    @SerializedName("id_item")
    var idItem: String? = null

    @SerializedName("id_location")
    var idLocation: String? = null

    @SerializedName("price_item")
    var priceItem: String? = null

    @SerializedName("stock_item")
    var stockItem: Int = 0

    @SerializedName("raceway_item")
    var raceway_item: String? = null


    @SerializedName("id_machine")
    var idMachine: String? = null

    @SerializedName("id_planogram")
    var idPlanogram: String? = null

    @SerializedName("descp_item")
    var descpItem: String? = null

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("id_good")
    var idGood: String? = null

    @SerializedName("images")
    var images: List<String>? = null

    override fun toString(): String {
        return "MachineItem{" +
                "name_item = '" + nameItem + '\'' +
                ",photos_item = '" + photosItem + '\'' +
                ",id_item = '" + idItem + '\'' +
                ",id_location = '" + idLocation + '\'' +
                ",price_item = '" + priceItem + '\'' +
                ",stock_item = '" + stockItem + '\'' +
                ",id_machine = '" + idMachine + '\'' +
                ",id_planogram = '" + idPlanogram + '\'' +
                ",descp_item = '" + descpItem + '\'' +
                ",id = '" + id + '\'' +
                ",id_good = '" + idGood + '\'' +
                "}"
    }
}