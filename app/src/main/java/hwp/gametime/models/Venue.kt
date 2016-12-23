package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("org.jsonschema2pojo")
class Venue constructor() : Parcelable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("abbreviation")
    @Expose
    var abbreviation: String? = null

    @SerializedName("capacity")
    @Expose
    var capacity: Int? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("field_type")
    @Expose
    var fieldType: Any? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("slug")
    @Expose
    var slug: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("stadium_type")
    @Expose
    var stadiumType: Any? = null

    @SerializedName("time_zone")
    @Expose
    var timeZone: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Venue> = object : Parcelable.Creator<Venue> {
            override fun createFromParcel(source: Parcel): Venue = Venue(source)
            override fun newArray(size: Int): Array<Venue?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
