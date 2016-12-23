package hwp.gametime.models

import android.os.Parcel
import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Generated("org.jsonschema2pojo")
data class Game(

        @SerializedName("id")
        @Expose
        var id: String? = null,


        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null,


        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null,


        @SerializedName("at_neutral_site")
        @Expose
        var atNeutralSite: String? = null,


        @SerializedName("attendance")
        @Expose
        var attendance: String? = null,


        @SerializedName("away_team_outcome")
        @Expose
        var awayTeamOutcome: String? = null,


        @SerializedName("away_team_score")
        @Expose
        var awayTeamScore: Int? = null,


        @SerializedName("broadcast")
        @Expose
        var broadcast: String? = null,


        @SerializedName("clock")
        @Expose
        var clock: String? = null,


        @SerializedName("clock_secs")
        @Expose
        var clockSecs: Int? = null,


        @SerializedName("daytime")
        @Expose
        var daytime: Boolean? = null,


        @SerializedName("duration")
        @Expose
        var duration: String? = null,


        @SerializedName("ended_at")
        @Expose
        var endedAt: String? = null,


        @SerializedName("home_team_outcome")
        @Expose
        var homeTeamOutcome: String? = null,


        @SerializedName("home_team_score")
        @Expose
        var homeTeamScore: Int? = null,


        @SerializedName("humidity")
        @Expose
        var humidity: String? = null,


        @SerializedName("interval")
        @Expose
        var interval: String? = null,


        @SerializedName("interval_number")
        @Expose
        var intervalNumber: String? = null,


        @SerializedName("interval_type")
        @Expose
        var intervalType: String? = null,


        @SerializedName("label")
        @Expose
        var label: String? = null,


        @SerializedName("name")
        @Expose
        var name: String? = null,


        @SerializedName("on")
        @Expose
        var on: String? = null,


        @SerializedName("period")
        @Expose
        var period: Int? = null,


        @SerializedName("period_label")
        @Expose
        var periodLabel: String? = null,


        @SerializedName("score")
        @Expose
        var score: String? = null,


        @SerializedName("score_differential")
        @Expose
        var scoreDifferential: Int? = null,


        @SerializedName("scoreline")
        @Expose
        var scoreline: String? = null,


        @SerializedName("slug")
        @Expose
        var slug: String? = null,


        @SerializedName("started_at")
        @Expose
        var startedAt: Date? = null,


        @SerializedName("status")
        @Expose
        var status: String? = null,


        @SerializedName("internet_coverage")
        @Expose
        var internetCoverage: String? = null,


        @SerializedName("satellite_coverage")
        @Expose
        var satelliteCoverage: String? = null,


        @SerializedName("television_coverage")
        @Expose
        var televisionCoverage: String? = null,


        @SerializedName("temperature")
        @Expose
        var temperature: String? = null,


        @SerializedName("temperature_unit")
        @Expose
        var temperatureUnit: String? = null,


        @SerializedName("timestamp")
        @Expose
        var timestamp: Int? = null,


        @SerializedName("title")
        @Expose
        var title: String? = null,


        @SerializedName("weather_conditions")
        @Expose
        var weatherConditions: String? = null,


        @SerializedName("wind_direction")
        @Expose
        var windDirection: String? = null,


        @SerializedName("wind_speed")
        @Expose
        var windSpeed: String? = null,


        @SerializedName("wind_speed_unit")
        @Expose
        var windSpeedUnit: String? = null,


        @SerializedName("home_team_id")
        @Expose
        var homeTeamId: String? = null,


        @SerializedName("away_team_id")
        @Expose
        var awayTeamId: String? = null,


        @SerializedName("winning_team_id")
        @Expose
        var winningTeamId: String? = null,


        @SerializedName("season_id")
        @Expose
        var seasonId: String? = null,


        @SerializedName("venue_id")
        @Expose
        var venueId: String? = null)
    : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Game> = object : Parcelable.Creator<Game> {
            override fun createFromParcel(source: Parcel): Game = Game(source)
            override fun newArray(size: Int): Array<Game?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readInt(), source.readString(), source.readString(), source.readInt(), source.readByte() == 1.toByte(), source.readString(), source.readString(), source.readString(), source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readInt(), source.readString(), source.readString(), source.readInt(), source.readString(), source.readString(), source.readSerializable() as Date, source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(createdAt)
        dest.writeString(updatedAt)
        dest.writeString(atNeutralSite)
        dest.writeString(attendance)
        dest.writeString(awayTeamOutcome)
        dest.writeInt(if (awayTeamScore == null) -1 else awayTeamScore!!)
        dest.writeString(broadcast)
        dest.writeString(clock)
        dest.writeInt(if (clockSecs == null) -1 else clockSecs!!)
        dest.writeByte(if (daytime!!) 1 else 0)
        dest.writeString(duration)
        dest.writeString(endedAt)
        dest.writeString(homeTeamOutcome)
        dest.writeInt(if (homeTeamScore == null) -1 else homeTeamScore!!)
        dest.writeString(humidity)
        dest.writeString(interval)
        dest.writeString(intervalNumber)
        dest.writeString(intervalType)
        dest.writeString(label)
        dest.writeString(name)
        dest.writeString(on)
        dest.writeInt(if (period == null) -1 else period!!)
        dest.writeString(periodLabel)
        dest.writeString(score)
        dest.writeInt(if (scoreDifferential == null) -1 else scoreDifferential!!)
        dest.writeString(scoreline)
        dest.writeString(slug)
        dest.writeSerializable(startedAt)
        dest.writeString(status)
        dest.writeString(internetCoverage)
        dest.writeString(satelliteCoverage)
        dest.writeString(televisionCoverage)
        dest.writeString(temperature)
        dest.writeString(temperatureUnit)
        dest.writeInt(if (timestamp == null) -1 else timestamp!!)
        dest.writeString(title)
        dest.writeString(weatherConditions)
        dest.writeString(windDirection)
        dest.writeString(windSpeed)
        dest.writeString(windSpeedUnit)
        dest.writeString(homeTeamId)
        dest.writeString(awayTeamId)
        dest.writeString(winningTeamId)
        dest.writeString(seasonId)
        dest.writeString(venueId)
    }
}
