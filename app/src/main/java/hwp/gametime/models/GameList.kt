package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GameList : Parcelable {
    @SerializedName("games")
    @Expose
    var games: List<Game> = ArrayList()

    @SerializedName("home_teams")
    @Expose
    var home_teams: List<Team> = ArrayList()

    @SerializedName("away_teams")
    @Expose
    var away_teams: List<Team> = ArrayList()

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<GameList> = object : Parcelable.Creator<GameList> {
            override fun createFromParcel(source: Parcel): GameList = GameList(source)
            override fun newArray(size: Int): Array<GameList?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
