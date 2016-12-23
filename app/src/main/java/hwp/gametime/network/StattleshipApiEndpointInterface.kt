package hwp.gametime.network

import hwp.gametime.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by dusan_cvetkovic on 11/23/16.
 */
interface StattleshipApiEndpointInterface {
    @GET("games")
    fun getGames(@Query("on") on: String): Observable<GameList>

    @GET("teams")
    fun getTeams(): Observable<TeamsList>

    @GET("team_season_stats")
    fun getTeamStats(@Query("team_id") teamID: String, @Query("on") on: String = "yesterday"): Observable<TeamSeasonStats>

    @GET("rosters")
    fun getPlayers(@Query("team_id") teamSlug: String): Observable<Players>

    //    https://api.stattleship.com/basketball/nba/game_logs?game_id=nba-2016-2017-min-ny-2016-12-2-1930
    @GET("game_logs")
    fun getGameLog(@Query("game_id") gameSlug: String): Observable<GameLogs>

//    companion object{
//        private
//        getInstance
//    }
}