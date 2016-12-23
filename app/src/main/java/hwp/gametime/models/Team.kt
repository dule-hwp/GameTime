package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hwp.gametime.adapters.SearchableAdapter

@Generated("org.jsonschema2pojo")
data class Team(

        @SerializedName("id")
        @Expose
        var id: String? = null,


        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null,


        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null,


        @SerializedName("color")
        @Expose
        var color: String? = null,


        @SerializedName("colors")
        @Expose
        var colors: List<String> = ArrayList(),


        @SerializedName("hashtag")
        @Expose
        var hashtag: String? = null,


        @SerializedName("hashtags")
        @Expose
        var hashtags: List<String> = ArrayList(),


        @SerializedName("location")
        @Expose
        var location: String? = null,


        @SerializedName("name")
        @Expose
        var name: String? = null,


        @SerializedName("nickname")
        @Expose
        var nickname: String? = null,


        @SerializedName("latitude")
        @Expose
        var latitude: Double? = null,


        @SerializedName("longitude")
        @Expose
        var longitude: Double? = null,


        @SerializedName("slug")
        @Expose
        var slug: String? = null,


        @SerializedName("division_id")
        @Expose
        var divisionId: String? = null,


        @SerializedName("league_id")
        @Expose
        var leagueId: String? = null
) : SearchableAdapter.SearchableItem, Parcelable {
        override fun satisfiesFilter(string: CharSequence): Boolean {
                return name?.contains(string, true)!! || nickname?.contains(string, true)!!
        }

        companion object {
                @JvmField val CREATOR: Parcelable.Creator<Team> = object : Parcelable.Creator<Team> {
                        override fun createFromParcel(source: Parcel): Team = Team(source)
                        override fun newArray(size: Int): Array<Team?> = arrayOfNulls(size)
                }
        }

        constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.createStringArrayList(), source.readString(), source.createStringArrayList(), source.readString(), source.readString(), source.readString(), source.readDouble(), source.readDouble(), source.readString(), source.readString(), source.readString())

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeString(id)
                dest?.writeString(createdAt)
                dest?.writeString(updatedAt)
                dest?.writeString(color)
                dest?.writeStringList(colors)
                dest?.writeString(hashtag)
                dest?.writeStringList(hashtags)
                dest?.writeString(location)
                dest?.writeString(name)
                dest?.writeString(nickname)
                dest?.writeDouble(latitude ?: 0.0)
                dest?.writeDouble(longitude ?: 0.0)
                dest?.writeString(slug)
                dest?.writeString(divisionId)
                dest?.writeString(leagueId)
        }

        override fun equals(other: Any?): Boolean {
                val team = other as? Team
                return id == team?.id
        }
}
