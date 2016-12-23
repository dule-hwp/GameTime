package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Generated("org.jsonschema2pojo")
class GameLog constructor() : Parcelable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("game_played")
    @Expose
    var gamePlayed: Boolean? = null

    @SerializedName("game_started")
    @Expose
    var gameStarted: Boolean? = null

    @SerializedName("home_team_outcome")
    @Expose
    var homeTeamOutcome: String? = null

    @SerializedName("home_team_score")
    @Expose
    var homeTeamScore: Int? = null

    @SerializedName("away_team_outcome")
    @Expose
    var awayTeamOutcome: String? = null

    @SerializedName("away_team_score")
    @Expose
    var awayTeamScore: Int? = null

    @SerializedName("team_outcome")
    @Expose
    var teamOutcome: String? = null

    @SerializedName("team_score")
    @Expose
    var teamScore: Int? = null

    @SerializedName("opponent_outcome")
    @Expose
    var opponentOutcome: String? = null

    @SerializedName("opponent_score")
    @Expose
    var opponentScore: Int? = null

    @SerializedName("is_home_team")
    @Expose
    var isHomeTeam: Boolean? = null

    @SerializedName("is_away_team")
    @Expose
    var isAwayTeam: Boolean? = null

    @SerializedName("assists")
    @Expose
    var assists: Int? = null

    @SerializedName("field_goals_attempted")
    @Expose
    var fieldGoalsAttempted: Int? = null

    @SerializedName("field_goals_made")
    @Expose
    var fieldGoalsMade: Int? = null

    @SerializedName("field_goals_pct")
    @Expose
    var fieldGoalsPct: Double? = null

    @SerializedName("free_throws_attempted")
    @Expose
    var freeThrowsAttempted: Int? = null

    @SerializedName("free_throws_made")
    @Expose
    var freeThrowsMade: Int? = null

    @SerializedName("free_throws_pct")
    @Expose
    var freeThrowsPct: Double? = null

    @SerializedName("points")
    @Expose
    var points: Int? = null

    @SerializedName("three_pointers_attempted")
    @Expose
    var threePointersAttempted: Int? = null

    @SerializedName("three_pointers_made")
    @Expose
    var threePointersMade: Int? = null

    @SerializedName("three_pointers_pct")
    @Expose
    var threePointersPct: Double? = null

    @SerializedName("turnovers")
    @Expose
    var turnovers: Int? = null

    @SerializedName("steals")
    @Expose
    var steals: Int? = null

    @SerializedName("blocks")
    @Expose
    var blocks: Int? = null

    @SerializedName("personal_fouls")
    @Expose
    var personalFouls: Int? = null

    @SerializedName("technical_fouls")
    @Expose
    var technicalFouls: Int? = null

    @SerializedName("time_played_total")
    @Expose
    var timePlayedTotal: Int? = null

    @SerializedName("plus_minus")
    @Expose
    var plusMinus: Int? = null

    @SerializedName("disqualifications")
    @Expose
    var disqualifications: Int? = null

    @SerializedName("rebounds_defensive")
    @Expose
    var reboundsDefensive: Int? = null

    @SerializedName("rebounds_offensive")
    @Expose
    var reboundsOffensive: Int? = null

    @SerializedName("rebounds_total")
    @Expose
    var reboundsTotal: Int? = null

    @SerializedName("double_double")
    @Expose
    var doubleDouble: Int? = null

    @SerializedName("double_triple_double")
    @Expose
    var doubleTripleDouble: Int? = null

    @SerializedName("double_twenty")
    @Expose
    var doubleTwenty: Int? = null

    @SerializedName("five_by_five")
    @Expose
    var fiveByFive: Int? = null

    @SerializedName("five_by_seven")
    @Expose
    var fiveBySeven: Int? = null

    @SerializedName("five_by_six")
    @Expose
    var fiveBySix: Int? = null

    @SerializedName("five_by_steals_blocks")
    @Expose
    var fiveByStealsBlocks: Int? = null

    @SerializedName("quadruple_double")
    @Expose
    var quadrupleDouble: Int? = null

    @SerializedName("quintuple_double")
    @Expose
    var quintupleDouble: Int? = null

    @SerializedName("thirty_thirty")
    @Expose
    var thirtyThirty: Int? = null

    @SerializedName("triple_double")
    @Expose
    var tripleDouble: Int? = null

    @SerializedName("game_id")
    @Expose
    var gameId: String? = null

    @SerializedName("player_id")
    @Expose
    var playerId: String? = null

    @SerializedName("team_id")
    @Expose
    var teamId: String? = null

    @SerializedName("opponent_id")
    @Expose
    var opponentId: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<GameLog> = object : Parcelable.Creator<GameLog> {
            override fun createFromParcel(source: Parcel): GameLog = GameLog(source)
            override fun newArray(size: Int): Array<GameLog?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
