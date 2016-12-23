package hwp.gametime.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import hwp.gametime.App
import hwp.gametime.R
import hwp.gametime.fragments.FavoritesFragment
import hwp.gametime.fragments.GamesFragment
import hwp.gametime.fragments.SearchFragment
import hwp.gametime.models.Game
import hwp.gametime.models.Team
import hwp.gametime.network.StattleshipApiEndpointInterface
import hwp.gametime.util.addFragment
import hwp.gametime.util.replaceFragment
import hwp.gametime.util.toast
import kotlinx.android.synthetic.main.activity_main2.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KFunction3


class MainActivity2 : AppCompatActivity(), GamesFragment.OnGamesFragmentInteractionListener {


    private var stattleshipService: StattleshipApiEndpointInterface? = null
    private var mCurrentBottomNavItemID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val app: App = application as App
        stattleshipService = app.stattleshipService

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_games -> {
                    fetchGamesForDate(Date(), MainActivity2::replaceFragmentFunction)
                }
                R.id.action_favourites -> {
                    val frag = FavoritesFragment.newInstance()
                    supportFragmentManager.replaceFragment(R.id.fragment_container, frag)
//                    toast("in progress")
                }
                R.id.action_search -> {
                    val frag = SearchFragment.newInstance()
                    supportFragmentManager.replaceFragment(R.id.fragment_container, frag)
                    registerForFilterMenu(frag)
                }
            }
            mCurrentBottomNavItemID = item.itemId
            when (mCurrentBottomNavItemID){
                R.id.action_games -> tabs.visibility = View.GONE
//                R.id.action_search, R.id.action_favourites -> tabs.visibility = View.VISIBLE
                else -> tabs.visibility = View.VISIBLE
            }
            invalidateOptionsMenu()
            true
        }

        setToolbar()

        fetchGamesForDate(Date(), MainActivity2::addFragmentFunction)
    }

    private fun registerForFilterMenu(frag: SearchView.OnQueryTextListener) {
        mListener = frag
    }

//    override fun onResume() {
//        super.onResume()
//        BusProvider.instance.register(this)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        BusProvider.instance.unregister(this)
//    }

    inner class SearchTextAvailableEvent internal constructor(val text: String)

    private fun setToolbar() {
        if (toolbar != null) {
            toolbar.navigationContentDescription = resources.getString(R.string.app_name)
            setSupportActionBar(toolbar)
        }
    }

    private fun fetchGamesForDate(date: Date, funToExecute: KFunction3<MainActivity2, Int, Fragment, Unit>) {
        val ft = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val subscribe = stattleshipService?.getGames(ft.format(date))
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            val gamesFragment = GamesFragment.newInstance(it, date)
                            funToExecute(this, R.id.fragment_container, gamesFragment)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )
        if (subscribe == null) {
            toast("No Api endpoint set")
        }
    }

    private var mListener: SearchView.OnQueryTextListener? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        if (mCurrentBottomNavItemID != R.id.action_search) {
            searchItem.isVisible = false
            return super.onCreateOptionsMenu(menu)
        }
        searchItem.isVisible = true
        mListener?.let {
            val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView
            searchView.setOnQueryTextListener(mListener)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onGameClick(game: Game, homeTeamSlug:Team?, awayTeamSlug:Team?) {
        val gameActivity = Intent(this, GameActivity::class.java)
        val b: Bundle = Bundle()
        b.putParcelable(GameActivity.EXTRA_GAME, game)
        homeTeamSlug?.let {
            b.putParcelable(GameActivity.HOME_TEAM, it)
        }
        awayTeamSlug?.let {
            b.putParcelable(GameActivity.AWAY_TEAM, it)
        }
        gameActivity.putExtras(b)
        startActivity(gameActivity)
    }

    override fun onTeamClick(team: Team) {
        val teamActivity = Intent(this, TeamActivity::class.java)
        teamActivity.putExtra(TeamActivity.EXTRA_TEAM, team)
//        teamActivity.putExtra(TeamActivity.EXTRA_TEAM_NAME, team.name)
//        teamActivity.putExtra(TeamActivity.EXTRA_TEAM_ID, team.id)
//        teamActivity.putExtra(TeamActivity.EXTRA_TEAM_COLOR, team.color)
        startActivity(teamActivity)
    }

    fun replaceFragmentFunction(id: Int, frag: Fragment) = supportFragmentManager.replaceFragment(id, frag)
    fun addFragmentFunction(id: Int, frag: Fragment) = supportFragmentManager.addFragment(newFragment = frag, fragmentContainerId = id)

    override fun onGamesDateChanged(date: Date) {
        fetchGamesForDate(date, MainActivity2::replaceFragmentFunction)
    }

    override fun onGamesFragmentInteraction(item: Game) {
        item.label?.let { this.toast(it) }
    }
}

