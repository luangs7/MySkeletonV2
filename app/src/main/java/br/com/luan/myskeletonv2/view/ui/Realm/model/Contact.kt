package br.com.luan.myskeletonv2.view.ui.Realm.model

import android.os.Parcel
import android.os.Parcelable
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.R.id.name
import br.com.luan.myskeletonv2.R.id.picture
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by Luan on 15/03/18.
 */
@RealmClass
open class Contact(
        @PrimaryKey
        var id: Int = 0,
        var picture: String = "",
        var name: String = "",
        var description: String = "",
        var conversation: RealmList<Conversa> = RealmList()


) : RealmObject(), Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(picture)
        writeString(name)
        writeString(description)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Contact> = object : Parcelable.Creator<Contact> {
            override fun createFromParcel(source: Parcel): Contact = Contact(source)
            override fun newArray(size: Int): Array<Contact?> = arrayOfNulls(size)
        }
    }
}