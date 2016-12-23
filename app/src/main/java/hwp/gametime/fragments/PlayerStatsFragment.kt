package hwp.gametime.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inqbarna.tablefixheaders.TableFixHeaders
import hwp.gametime.R
import hwp.gametime.adapters.PlayerStatsTableAdapter
import hwp.gametime.models.GameLogs
import hwp.gametime.util.toast

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
class PlayerStatsFragment : Fragment() {

    //    private var mColumnCount = 1
    private var mListener: OnPlayersFragmentInteractionListener? = null

    private var mGameLogs: GameLogs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mGameLogs = arguments.getParcelable<GameLogs>(ARG_GAME_LOGS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_players_list, container, false)

        // Set the adapter
        if (view is TableFixHeaders){
//            val tableFixHeaders = findViewById(R.id.table) as TableFixHeaders
            mGameLogs?.let {
                if (it.homeTeams.isEmpty()) {
                    activity.finish()
                    activity.toast("No game data available!!!")
                    return null
                }
                view.adapter = PlayerStatsTableAdapter(this.context, it)
            }
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
//        fun onPlayerClick(player: Player)
    }

    companion object {

        private val ARG_GAME_LOGS = "game_logs"

        fun newInstance(gameLogs: GameLogs): PlayerStatsFragment {
            val fragment = PlayerStatsFragment()
            val args = Bundle()
            args.putParcelable(ARG_GAME_LOGS, gameLogs)
            fragment.arguments = args
            return fragment
        }
    }
}
