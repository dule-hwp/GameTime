package hwp.gametime.fragments

import android.os.Bundle
import android.util.Log
import hwp.gametime.R
import hwp.gametime.adapters.SearchableAdapter
import hwp.gametime.models.Player
import hwp.gametime.models.Team
import hwp.gametime.util.Const
import hwp.gametime.util.toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by dusan_cvetkovic on 12/9/16.
 */
class SearchablePlayersFragment : SearchableBaseFragment() {

    private var mPlayers: MutableMap<String, Player>
    private var mNumOfRequests: Int = -1

    init {
        mPlayers = mutableMapOf<String, Player>()
    }

    override fun handleClick(item: SearchableAdapter.SearchableItem?) {
        val player = item as? Player
        activity.toast("Player view" + player?.slug)
    }

    override fun getTitle(): String {
        return Const.PLAYERS
    }

    override fun onResume() {
        super.onResume()

        app?.stattleshipService?.getTeams()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            mNumOfRequests = it.teams.size
                            it.teams.forEach {
                                Log.d(this.javaClass.name + " sent req: ", it.nickname)
                                fetchRoster(it)
                            }
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )


    }

    private fun fetchRoster(team: Team) {
        team.slug?.let {
            app?.stattleshipService?.getPlayers(it)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(
                            {
                                if (mSearchableAdapter == null && mNumOfRequests == 1) {
//                                    mPlayers.addAll(it.players)
                                    it.players.forEach {
                                        mPlayers.put(it.id.toString(), it)
                                    }

                                    Log.d(this.javaClass.name + " init: ", mPlayers.size.toString())
                                    mSearchableAdapter = SearchableAdapter(mPlayers.values.toList(), this)
                                    recyclerView?.adapter = mSearchableAdapter
                                } else {
                                    Log.d(this.javaClass.name + " added", it.players.size.toString())
                                    Log.d(this.javaClass.name + " requests left", mNumOfRequests.toString())
//                                    mPlayers.addAll(it.players)
                                    it.players.forEach {
                                        mPlayers.put(it.id.toString(), it)
                                    }
                                }
                                mNumOfRequests--

                            },
                            { error ->
                                Log.e("Error", error.message)
                                mNumOfRequests--
                            }
                    )
        }
    }

    companion object {
        private val ARG_FRAGMENT_INDEX = "items"
        fun newInstance(): SearchableBaseFragment {
            val fragment = SearchablePlayersFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}