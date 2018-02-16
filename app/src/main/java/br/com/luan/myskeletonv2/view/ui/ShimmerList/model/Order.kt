package br.com.luan.myskeletonv2.view.ui.ShimmerList.model

import com.google.gson.annotations.SerializedName

class Order {

    @SerializedName("delivery")
    var delivery: String? = null

    @SerializedName("payment_method_id")
    var paymentMethodId: Int = 0

    @SerializedName("id")
    var id: String? = null

    @SerializedName("is_delivery")
    var isIsDelivery: Boolean = false

    @SerializedName("status")
    var status: String? = null

    @SerializedName("quantity")
    var quantity: String? = null

    @SerializedName("order_number")
    var orderNumber: String? = null

    @SerializedName("payment_method")
    var paymentMethod: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("price_total")
    var priceTotal: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("size")
    var size: String? = null

    @SerializedName("color")
    var color: String? = null


    override fun toString(): String {
        return "Order{" +
                "delivery = '" + delivery + '\'' +
                ",payment_method_id = '" + paymentMethodId + '\'' +
                ",id = '" + id + '\'' +
                ",is_delivery = '" + isIsDelivery + '\'' +
                ",status = '" + status + '\'' +
                "}"
    }
}