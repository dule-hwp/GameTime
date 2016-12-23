package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Players constructor() : Parcelable {
    @SerializedName("players")
    @Expose
    var players = ArrayList<Player>()

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Players> = object : Parcelable.Creator<Players> {
            override fun createFromParcel(source: Parcel): Players = Players(source)
            override fun newArray(size: Int): Array<Players?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
