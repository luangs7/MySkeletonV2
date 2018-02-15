package br.com.luan.myskeletonv2.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Product(
        @SerializedName("is_highlight")
        var isIsHighlight: Boolean = false,

        @SerializedName("image")
        var image: String? = null,

        @SerializedName("is_enable_production")
        var isIsEnableProduction: Boolean = false,

        @SerializedName("images")
        var images: List<String>? = null,

        @SerializedName("code")
        var code: String? = null,
        @SerializedName("color")
        var color: String? = null,

        @SerializedName("color_secundary_id")
        var colorSecundaryId: String? = null,

        @SerializedName("is_new")
        var isIsNew: Boolean = false,

        @SerializedName("price_promotion")
        var pricePromotion: String? = null,

        @SerializedName("description")
        var description: String? = null,

        @SerializedName("collection")
        var collection: String? = null,

        @SerializedName("collection_id")
        var collectionId: String? = null,

        @SerializedName("subcategory_id")
        var subcategoryId: String? = null,

        @SerializedName("color_id")
        var colorId: String? = null,

        @SerializedName("category_id")
        var categoryId: String? = null,


        @SerializedName("price")
        var price: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("is_promotion")
        var isIsPromotion: Boolean = false,

        @SerializedName("id")
        var id: String? = null,

        @SerializedName("category")
        var category: String? = null,

        @SerializedName("subcategory")
        var subcategory: String? = null,

        @SerializedName("color_secundary")
        var colorSecundary: String? = null,

        @SerializedName("size")
        var size: String? = null,

        @SerializedName("quantity")
        var quantity: String? = null,

        @SerializedName("sizes_search")
        var sizessearch: List<String> = ArrayList()


) : Parcelable {
    constructor(source: Parcel) : this(
            1 == source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.createStringArrayList(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (isIsHighlight) 1 else 0))
        writeString(image)
        writeInt((if (isIsEnableProduction) 1 else 0))
        writeStringList(images)
        writeString(code)
        writeString(color)
        writeString(colorSecundaryId)
        writeInt((if (isIsNew) 1 else 0))
        writeString(pricePromotion)
        writeString(description)
        writeString(collection)
        writeString(collectionId)
        writeString(subcategoryId)
        writeString(colorId)
        writeString(categoryId)
        writeString(price)
        writeString(name)
        writeInt((if (isIsPromotion) 1 else 0))
        writeString(id)
        writeString(category)
        writeString(subcategory)
        writeString(colorSecundary)
        writeString(size)
        writeString(quantity)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Product> = object : Parcelable.Creator<Product> {
            override fun createFromParcel(source: Parcel): Product = Product(source)
            override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
        }
    }
}











