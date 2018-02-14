package br.com.luan.myskeletonv2.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Machine(

        @SerializedName("loc_city")
        var locCity: String? = null,

        @SerializedName("loc_street")
        var locStreet: String? = null,

        @SerializedName("loc_state")
        var locState: String? = null,

        @SerializedName("loc_complement")
        var locComplement: String? = null,

        @SerializedName("loc_lat")
        var locLat: String? = null,

        @SerializedName("id_location")
        var idLocation: String? = null,

        @SerializedName("loc_neighborhood")
        var locNeighborhood: String? = null,

        @SerializedName("loc_number")
        var locNumber: String? = null,

        @SerializedName("loc_long")
        var locLong: String? = null,

        @SerializedName("loc_zipcode")
        var locZipcode: String? = null,

        @SerializedName("id_machine")
        var idMachine: String? = null,

        @SerializedName("loc_name")
        var locName: String? = null,

        @SerializedName("id")
        var id: Int = 0


) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(locCity)
        writeString(locStreet)
        writeString(locState)
        writeString(locComplement)
        writeString(locLat)
        writeString(idLocation)
        writeString(locNeighborhood)
        writeString(locNumber)
        writeString(locLong)
        writeString(locZipcode)
        writeString(idMachine)
        writeString(locName)
        writeInt(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Machine> = object : Parcelable.Creator<Machine> {
            override fun createFromParcel(source: Parcel): Machine = Machine(source)
            override fun newArray(size: Int): Array<Machine?> = arrayOfNulls(size)
        }
    }
}


