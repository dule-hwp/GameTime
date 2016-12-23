package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("org.jsonschema2pojo")
class GameLogs constructor() : Parcelable {
    @SerializedName("games")
    @Expose
    var games: List<Game> = ArrayList()

    @SerializedName("home_teams")
    @Expose
    var homeTeams: List<Team> = ArrayList()

    @SerializedName("away_teams")
    @Expose
    var awayTeams: List<Team> = ArrayList()

    @SerializedName("winning_teams")
    @Expose
    var winningTeams: List<Team> = ArrayList()

    @SerializedName("venues")
    @Expose
    var venues: List<Venue> = ArrayList()

    @SerializedName("players")
    @Expose
    var players: List<Player> = ArrayList()

    @SerializedName("game_logs")
    @Expose
    var gameLogs: List<GameLog> = ArrayList()

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<GameLogs> = object : Parcelable.Creator<GameLogs> {
            override fun createFromParcel(source: Parcel): GameLogs = GameLogs(source)
            override fun newArray(size: Int): Array<GameLogs?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
