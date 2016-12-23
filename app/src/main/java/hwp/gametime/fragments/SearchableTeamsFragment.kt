package hwp.gametime.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import hwp.gametime.R
import hwp.gametime.activities.TeamActivity
import hwp.gametime.adapters.SearchableAdapter
import hwp.gametime.models.Team
import hwp.gametime.util.Const
import hwp.gametime.util.toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.reflect.KClass

/**
 * Created by dusan_cvetkovic on 12/9/16.
 */
class SearchableTeamsFragment private constructor() : SearchableBaseFragment() {
    override fun handleClick(item: SearchableAdapter.SearchableItem?) {
        val team = item as? Team
        activity.toast("Team view " + team?.slug)
        team?.let {
            val teamActivity = Intent(context, TeamActivity::class.java)
            teamActivity.putExtra(TeamActivity.EXTRA_TEAM, it)
            startActivity(teamActivity)
        }

    }

    override fun getTitle(): String {
        return Const.TEAMS
    }

    override fun onResume() {
        super.onResume()
        app?.stattleshipService?.getTeams()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            val items = it.teams as? MutableList<SearchableAdapter.SearchableItem>
                            items?.let {
                                mSearchableAdapter = SearchableAdapter(it, this)
                                recyclerView?.adapter = mSearchableAdapter
                            }
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )
    }

    companion object {
        private val ARG_FRAGMENT_INDEX = "items"
        fun newInstance(): SearchableBaseFragment {
            val fragment = SearchableTeamsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}