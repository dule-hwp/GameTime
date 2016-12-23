package hwp.gametime.fragments

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hwp.gametime.R
import hwp.gametime.models.Player
import hwp.gametime.models.Team
import hwp.gametime.util.getSetFromPrefs

class FavouritePagerItemFragment(var mTitle: String) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_favouritepageritem_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

//            val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
            initAdapter(context, view)
        }
        return view
    }

    private fun initAdapter(context: Context, view: RecyclerView) {
        val set = activity.getSetFromPrefs(mTitle)
        if (mTitle == getString(R.string.teams))
            view.adapter = FavouritePagerItemAdapter(context, set.toList(), Team::class.java)
        else if (mTitle == getString(R.string.players))
            view.adapter = FavouritePagerItemAdapter(context, set.toList(), Player::class.java)
    }

    private var mRecyclerView: RecyclerView? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view as? RecyclerView
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView?.let {
            initAdapter(context, it)
        }

    }

}
