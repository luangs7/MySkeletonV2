package br.com.luan.myskeletonv2.data.model

import com.google.gson.annotations.SerializedName

class Rating() {

	@SerializedName("price")
	var price: Float? = null

	@SerializedName("machine_id")
	var machineId: Int? = null

	@SerializedName("location")
	var location: Float? = null

	@SerializedName("variation")
	var variation: Float? = null
}