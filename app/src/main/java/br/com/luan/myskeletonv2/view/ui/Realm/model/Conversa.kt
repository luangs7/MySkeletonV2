package br.com.luan.myskeletonv2.view.ui.Realm.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by Luan on 15/03/18.
 */
@RealmClass
open class Conversa(
        var message: String = "",
        var date: String = "",
        var idOwner: Int = 0,
        @PrimaryKey
        var id: Int = 0

) : RealmObject(), Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(message)
        writeString(date)
        writeInt(idOwner)
        writeInt(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Conversa> = object : Parcelable.Creator<Conversa> {
            override fun createFromParcel(source: Parcel): Conversa = Conversa(source)
            override fun newArray(size: Int): Array<Conversa?> = arrayOfNulls(size)
        }
    }
}

