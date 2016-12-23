package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hwp.gametime.adapters.SearchableAdapter

@Generated("org.jsonschema2pojo")
class Player constructor() : Parcelable, SearchableAdapter.SearchableItem {

    override fun satisfiesFilter(string: CharSequence): Boolean {
        name?.let {
            return it.contains(string, true)
        }
        return false
    }

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("active")
    @Expose
    var active: Boolean? = null

    @SerializedName("birth_date")
    @Expose
    var birthDate: Any? = null

    @SerializedName("captain")
    @Expose
    var captain: Any? = null

    @SerializedName("city")
    @Expose
    var city: Any? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("draft_season")
    @Expose
    var draftSeason: Any? = null

    @SerializedName("draft_round")
    @Expose
    var draftRound: Any? = null

    @SerializedName("draft_overall_pick")
    @Expose
    var draftOverallPick: Any? = null

    @SerializedName("draft_team_name")
    @Expose
    var draftTeamName: Any? = null

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("handedness")
    @Expose
    var handedness: String? = null

    @SerializedName("bats")
    @Expose
    var bats: String? = null

    @SerializedName("height")
    @Expose
    var height: Any? = null

    @SerializedName("high_school")
    @Expose
    var highSchool: Any? = null

    @SerializedName("unit_of_height")
    @Expose
    var unitOfHeight: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("nickname")
    @Expose
    var nickname: String? = null

    @SerializedName("position_abbreviation")
    @Expose
    var positionAbbreviation: String? = null

    @SerializedName("position_name")
    @Expose
    var positionName: String? = null

    @SerializedName("salary")
    @Expose
    var salary: Any? = null

    @SerializedName("humanized_salary")
    @Expose
    var humanizedSalary: String? = null

    @SerializedName("salary_currency")
    @Expose
    var salaryCurrency: String? = null

    @SerializedName("school")
    @Expose
    var school: Any? = null

    @SerializedName("slug")
    @Expose
    var slug: String? = null

    @SerializedName("sport")
    @Expose
    var sport: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("weight")
    @Expose
    var weight: Any? = null

    @SerializedName("uniform_number")
    @Expose
    var uniformNumber: String? = null

    @SerializedName("unit_of_weight")
    @Expose
    var unitOfWeight: String? = null

    @SerializedName("years_of_experience")
    @Expose
    var yearsOfExperience: Any? = null

    @SerializedName("pro_debut")
    @Expose
    var proDebut: Any? = null

    @SerializedName("league_id")
    @Expose
    var leagueId: String? = null

    @SerializedName("playing_position_id")
    @Expose
    var playingPositionId: Any? = null

    @SerializedName("team_id")
    @Expose
    var teamId: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Player> = object : Parcelable.Creator<Player> {
            override fun createFromParcel(source: Parcel): Player = Player(source)
            override fun newArray(size: Int): Array<Player?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
}
