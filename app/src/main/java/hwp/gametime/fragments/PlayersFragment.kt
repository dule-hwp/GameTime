package hwp.gametime.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inqbarna.tablefixheaders.TableFixHeaders
import hwp.gametime.R
import hwp.gametime.activities.TeamActivity
import hwp.gametime.adapters.MyPlayersRecyclerViewAdapter
import hwp.gametime.adapters.PlayersTableAdapter
import hwp.gametime.models.Player
import hwp.gametime.models.Players

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnPlayersFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class PlayersFragment : Fragment() {

    //    private var mColumnCount = 1
    private var mListener: OnPlayersFragmentInteractionListener? = null
    private var players: Players? = null

    private var teamColor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            players = arguments.getParcelable<Players>(ARG_PLAYERS)
            teamColor = arguments.getString(EXTRA_TEAM_COLOR)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_players_list, container, false)

        // Set the adapter
        val context = view.getContext()
        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(context)
            view.adapter = players?.players?.let { MyPlayersRecyclerViewAdapter(it, mListener) }
        }
        else if (view is TableFixHeaders){
//            val tableFixHeaders = findViewById(R.id.table) as TableFixHeaders
            players?.let { view.adapter = PlayersTableAdapter(this.context, it, teamColor) }
        }
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPlayersFragmentInteractionListener) {
            mListener = context as OnPlayersFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnPlayersFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnPlayersFragmentInteractionListener {
        fun onPlayerClick(player: Player)
    }

    companion object {

        private val ARG_PLAYERS = "players"
        private val EXTRA_TEAM_COLOR = "EXTRA_TEAM_COLOR"

        fun newInstance(players: Players, color:String?): PlayersFragment {
            val fragment = PlayersFragment()
            val args = Bundle()
            args.putParcelable(ARG_PLAYERS, players)
            args.putString(EXTRA_TEAM_COLOR, color)
            fragment.arguments = args
            return fragment
        }
    }
}
