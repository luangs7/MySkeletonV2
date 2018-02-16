package br.com.luan.myskeletonv2.view.ui.FindMovie

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmModel
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Filme(
        @SerializedName("poster_path")
        @Expose
        var posterPath: String? = null,

        @SerializedName("adult")
        @Expose
        var adult: Boolean? = null,

        @SerializedName("overview")
        @Expose
        var overview: String? = null,
        @SerializedName("release_date")
        @Expose
        var releaseDate: String? = null,

        @SerializedName("genre_ids")
        @Expose
        @Ignore
        var genreIds: List<String>? = null,

        @SerializedName("id")
        @Expose
        @PrimaryKey
        var id: String? = null,

        @SerializedName("original_title")
        @Expose
        var originalTitle: String? = null,

        @SerializedName("original_language")
        @Expose
        var originalLanguage: String? = null,

        @SerializedName("title")
        @Expose
        var title: String? = null,

        @SerializedName("backdrop_path")
        @Expose
        private var backdropPath: String? = null,

        @SerializedName("popularity")
        @Expose
        var popularity: String? = null,
        @SerializedName("vote_count")
        @Expose
        var voteCount: String? = null,

        @SerializedName("video")
        @Expose
        var video: Boolean? = null,

        @SerializedName("vote_average")
        @Expose
        var voteAverage: String? = null


) : RealmModel, Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(posterPath)
        writeValue(adult)
        writeString(overview)
        writeString(releaseDate)
        writeStringList(genreIds)
        writeString(id)
        writeString(originalTitle)
        writeString(originalLanguage)
        writeString(title)
        writeString(backdropPath)
        writeString(popularity)
        writeString(voteCount)
        writeValue(video)
        writeString(voteAverage)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Filme> = object : Parcelable.Creator<Filme> {
            override fun createFromParcel(source: Parcel): Filme = Filme(source)
            override fun newArray(size: Int): Array<Filme?> = arrayOfNulls(size)
        }
    }
}

