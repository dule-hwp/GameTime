package hwp.gametime.activities

import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.caverock.androidsvg.SVG
import hwp.gametime.App
import hwp.gametime.R
import hwp.gametime.fragments.PlayerStatsFragment
import hwp.gametime.models.Game
import hwp.gametime.models.Team
import hwp.gametime.network.StattleshipApiEndpointInterface
import hwp.gametime.util.Util
import hwp.gametime.util.toast
import hwp.gametime.util.addFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.InputStream
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), PlayerStatsFragment.OnPlayersFragmentInteractionListener {

    private var mGame: Game? = null
    private var mAwayTeam: Team? = null
    private var mHomeTeam: Team? = null

    companion object {
        val EXTRA_GAME: String = "extra_game"
        val HOME_TEAM: String = "extra_home_slug"
        val AWAY_TEAM: String = "extra_away_slug"
    }

    private var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable>? = null
    private var stattleshipService: StattleshipApiEndpointInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initProperties()

        initViews()

        fetchGameStats()

//        setSupportActionBar(toolbar)
    }


    private fun initProperties() {
        val app: App = application as App
        stattleshipService = app.stattleshipService

        requestBuilder = Util.getRequestBuilder(this)
        mGame = intent.extras.getParcelable(EXTRA_GAME)
        mHomeTeam = intent.extras.getParcelable(HOME_TEAM)
        mAwayTeam = intent.extras.getParcelable(AWAY_TEAM)

    }

    private fun initViews() {
//        teamColor?.let {
//            app_bar.setBackgroundColor(Color.parseColor("#EE" + it.toUpperCase()))
//        }

        title = "test"
        mHomeTeam?.let {
            loadLogo(it, ivHomeTeamLogo)
        }
        mAwayTeam?.let {
            loadLogo(it, ivAwayTeamLogo)
        }
    }

    private fun loadLogo(teamSlug: Team?, iv: ImageView) {
        teamSlug?.slug?.let {
            val teamLogo = Util.getTeamLogoUri(it)
            requestBuilder!!
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so teamSlug's not worth to cache teamSlug
                    .load(teamLogo)
                    .into(iv)
        }

        val isItHomeTeam = mGame?.homeTeamId == teamSlug?.id
        val score = if (isItHomeTeam) mGame?.homeTeamScore else mGame?.awayTeamScore
        score?.let {
            if (isItHomeTeam) tvHomeScore.text = it.toString()
            else tvAwayScore.text = it.toString()
        }

        teamSlug?.color?.let {
            val color = Color.parseColor("#EE" + it.toUpperCase())
            iv.setBackgroundColor(color)
            if (isItHomeTeam) tvHomeScore.setBackgroundColor(color)
            else tvAwayScore.setBackgroundColor(color)
        }

    }

    private fun fetchGameStats() {
        val subscribe = mGame?.slug?.let {
            stattleshipService?.getGameLog(it)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(
                            {
                                toast("Successfully fetched game stats")
                                val psf = PlayerStatsFragment.newInstance(it)
                                supportFragmentManager.addFragment(R.id.fragment_container, psf)
                            },
                            { error ->
                                Log.e("Error", error.message)
                            }
                    )
        }
        if (subscribe == null) {
            toast("No Api endpoint set")
        }
    }
}
