package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeamsList : Parcelable {
    @SerializedName("teams")
    @Expose
    var teams: List<Team> = ArrayList()

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<TeamsList> = object : Parcelable.Creator<TeamsList> {
            override fun createFromParcel(source: Parcel): TeamsList = TeamsList(source)
            override fun newArray(size: Int): Array<TeamsList?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
