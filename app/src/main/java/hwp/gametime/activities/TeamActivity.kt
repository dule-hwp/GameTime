package hwp.gametime.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.caverock.androidsvg.SVG
import com.google.gson.Gson
import hwp.gametime.App
import hwp.gametime.R
import hwp.gametime.fragments.PlayersFragment
import hwp.gametime.models.Player
import hwp.gametime.models.Team
import hwp.gametime.network.ServiceGenerator
import hwp.gametime.network.StattleshipApiEndpointInterface
import hwp.gametime.util.*
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.content_team.*
import kotlinx.android.synthetic.main.team_view_single_stat_category.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.InputStream

class TeamActivity : AppCompatActivity(), PlayersFragment.OnPlayersFragmentInteractionListener {
    override fun onPlayerClick(player: Player) {
        player.firstName?.let { toast(it) }
    }

    companion object {
        val EXTRA_TEAM: String = "extra_team"
    }

    private var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>? = null
    private var stattleshipService: StattleshipApiEndpointInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        initProperties()
        initViews()

        mTeam?.slug?.let {
            val teamLogo = Util.getTeamLogoUri(it)
            requestBuilder!!
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(teamLogo)
                    .into(ivTeamLogo)
            fetchTeamRoster(it)
            fetchTeamStats(it)
        }

        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            mTeam?.let {
                onFavoriteButtonClick()
                val snackText = if (mIsFavoriteTeam) " added to favorites"
                                else " removed from favorites"
                Snackbar.make(view, it.name!! + snackText , Snackbar.LENGTH_LONG)
                        .setAction("Undo", { onFavoriteButtonClick() }).show()
            }
        }
    }

    private fun onFavoriteButtonClick() {
        mTeam?.let {
            if (mIsFavoriteTeam) {
                mFavTeams?.remove(it)
                mIsFavoriteTeam = false
            } else {
                mFavTeams?.add(it)
                mIsFavoriteTeam = true
            }
        }
        refreshFavDrawable()
    }

    override fun onStop() {
        super.onStop()
        mFavTeams?.map { Gson().toJson(it) }?.toSet()?.let {
            saveToPrefs(Const.TEAMS, it)
        }

    }

    private var mIsFavoriteTeam: Boolean = false

    private var mTeam: Team? = null

//    private var mFavTeams: List<Team>

    private var mFavTeams: MutableList<Team>? = null

    private fun initProperties() {
        val app: App = application as App
        stattleshipService = app.stattleshipService

        requestBuilder = Util.getRequestBuilder(this)
        mTeam = intent.getParcelableExtra(EXTRA_TEAM)
        if (mTeam != null) {
            val teamsInPrefs = this.getSetFromPrefs(Const.TEAMS)
            mFavTeams = teamsInPrefs.map { Gson().fromJson(it, Team::class.java) } as MutableList<Team>
            mIsFavoriteTeam = mFavTeams?.find { it.id == mTeam?.id } != null
        }

    }

    private fun initViews() {
        points_per_game.tvStatHeader.text = "PPG"
        assists_per_game.tvStatHeader.text = "APG"
        rebounds_per_game.tvStatHeader.text = "RPG"
        mTeam?.color?.let {
            app_bar.setBackgroundColor(Color.parseColor("#EE" + it.toUpperCase()))
        }
        title = ""

        refreshFavDrawable()

    }

    private fun refreshFavDrawable() {
        fab.setImageDrawable(ContextCompat.getDrawable(this,
                if (mIsFavoriteTeam) R.drawable.ic_favorite_black_24dp
                else R.drawable.ic_favorite_border_black_24dp))
    }

    private fun fetchTeamRoster(teamSlug: String) {
        val subscribe = stattleshipService?.getPlayers(teamSlug)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.map {
                    val players = it.players.filter { it.teamId == mTeam?.id }
                    it.players.clear()
                    it.players.addAll(players)
                    it
                }
                ?.subscribe(
                        {
                            val playersFragment = PlayersFragment.newInstance(it, mTeam?.color)
                            supportFragmentManager.addFragment(R.id.fragment_container, playersFragment)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )
        if (subscribe == null) {
            toast("No Api endpoint set")
        }
    }

    private fun fetchTeamStats(teamSlug: String) {
        val subscribe = stattleshipService?.getTeamStats(teamSlug)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            val teamStats = it
                            teamStats?.teamSeasonStats?.get(0)?.let {
                                points_per_game.tvStatValue.text = it.averagePoints.toString()
                                assists_per_game.tvStatValue.text = it.averageAssists.toString()
                                rebounds_per_game.tvStatValue.text = it.averageRebounds.toString()
                            }

                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )
        if (subscribe == null) {
            toast("No Api endpoint set")
        }
    }
}
