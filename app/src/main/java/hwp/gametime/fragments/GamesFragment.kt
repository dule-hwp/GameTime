package hwp.gametime.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hwp.gametime.R
import hwp.gametime.adapters.GamesRecyclerViewAdapter
import hwp.gametime.models.Game
import hwp.gametime.models.GameList
import hwp.gametime.models.Team
import hwp.gametime.util.addDays

import kotlinx.android.synthetic.main.fragment_games_list.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnGamesFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class GamesFragment : Fragment() {
    // parameters
    private var mGames: GameList? = null
    private var mListener: OnGamesFragmentInteractionListener? = null
    private var mGamesDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mGames = arguments.getParcelable<GameList>(ARG_GAMES)
            mGamesDate = Date(arguments.getLong(ARG_GAME_DAY))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_games_list, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener { mListener?.onGamesDateChanged(mGamesDate!!) }

        val ft = SimpleDateFormat("EEE, MMM d", Locale.US)
        if (mGamesDate == null)
            mGamesDate = Date()

        val dateHeader = getString(R.string.date_header)
        val spanned = Html.fromHtml(dateHeader.format(ft.format(mGamesDate), mGames?.games?.size))
        tvGamesDate.text = spanned


        btnPreviousDay.setOnClickListener {
            mGamesDate!!.addDays(-1)
            mListener?.onGamesDateChanged(mGamesDate!!)
        }
        btnNextDay.setOnClickListener {
            mGamesDate!!.addDays(1)
            mListener?.onGamesDateChanged(mGamesDate!!)
        }

        if (mGames != null){
            mGames?.games = mGames?.games?.reversed()!!
            mGames?.away_teams= mGames?.away_teams?.reversed()!!
            mGames?.home_teams= mGames?.home_teams?.reversed()!!
            updateView(rvGames, mGames!!)
        }
    }

    private fun updateView(view: RecyclerView, games: GameList) {
        view.adapter = GamesRecyclerViewAdapter(games, mListener)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnGamesFragmentInteractionListener) {
            mListener = context as OnGamesFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnGamesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnGamesFragmentInteractionListener {
        fun onGamesFragmentInteraction(item: Game)
        fun onGamesDateChanged(date: Date)
        fun onTeamClick(team: Team)
        fun onGameClick(game: Game, homeTeamSlug:Team?, awayTeamSlug:Team?)
    }

    companion object {

        // parameter argument names
        private val ARG_GAME_DAY = "game-day"
        private val ARG_GAMES = "games"

        fun newInstance(games: Parcelable, date: Date? = null): GamesFragment {
            val fragment = GamesFragment()
            val args = Bundle()
            args.putLong(ARG_GAME_DAY, date?.time as Long)
            args.putParcelable(ARG_GAMES, games)
            fragment.arguments = args
            return fragment
        }
    }
}
