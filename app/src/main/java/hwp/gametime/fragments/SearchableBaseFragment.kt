package hwp.gametime.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.google.gson.Gson
import hwp.gametime.App
import hwp.gametime.R
import hwp.gametime.activities.MainActivity2
import hwp.gametime.adapters.SearchableAdapter
import hwp.gametime.adapters.SearchableAdapter.SearchableItem
import hwp.gametime.models.Player
import hwp.gametime.models.Team
import hwp.gametime.util.Const
import hwp.gametime.util.getSetFromPrefs
import hwp.gametime.util.saveToPrefs
import hwp.gametime.util.toast


abstract class SearchableBaseFragment : Fragment(), SearchableAdapter.OnItemTapInteraction {

    protected var recyclerView: RecyclerView? = null
    protected var app: App? = null
    private var mActionMode: ActionMode? = null
    private var isMultiSelectMode: Boolean = false
    private var multiSelectList: MutableList<SearchableItem>? = null

    override fun onLongClick(position: Int) {

        if (!isMultiSelectMode) {
            multiSelectList = mutableListOf<SearchableItem>()

            activity.getSetFromPrefs(getTitle()).forEach {

                when (getTitle()){
                    Const.TEAMS -> {
                        val jsonValue = Gson().fromJson(it, Team::class.java)
                        multiSelectList?.let { it.add(jsonValue) }
                    }
                    Const.PLAYERS -> {
                        val jsonValue = Gson().fromJson(it, Player::class.java)
                        multiSelectList?.let { it.add(jsonValue) }
                    }
                }
            }
            isMultiSelectMode = true

            if (mActionMode == null) {
                val mainActivity = activity as? MainActivity2
                mActionMode = mainActivity?.startSupportActionMode(mActionModeCallback)
            }
        }
        multi_select(position)
    }

    abstract fun handleClick(item: SearchableItem?)
    abstract fun getTitle() : String

    override fun onClick(position: Int) {
        if (isMultiSelectMode)
            multi_select(position)
        else {
            val item = mSearchableAdapter?.filteredItems?.get(position)
            handleClick(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity.application as App
    }

    protected var mSearchableAdapter: SearchableAdapter? = null

    fun performFiltering(searchText: String) {
        Log.d(this.javaClass.name, "got text: " + searchText)
        mSearchableAdapter?.let {
            it.filter?.filter(searchText)
        }
    }

    fun multi_select(position: Int) {
        if (mActionMode != null) {
            val filtered_list = mSearchableAdapter?.filteredItems
            multiSelectList?.let {
                if (filtered_list != null) {
                    if (it.contains(filtered_list[position]))
                        it.remove(filtered_list[position])
                    else
                        it.add(filtered_list[position])
                }

                if (it.size > 0)
                    mActionMode?.title = "Selected " + it.size
                else
                    mActionMode?.title = ""
            }
            refreshAdapter()
        }
    }

    fun refreshAdapter() {
        mSearchableAdapter?.selectedItems = multiSelectList
        mSearchableAdapter?.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recyclerView = inflater!!.inflate(R.layout.recycler_view, container, false) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
        recyclerView?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        return recyclerView
    }

    private val mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.menu_context_favorites, menu)
//            mContextMenu = menu
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_action_favorites -> {
                    val favoritesSet = mutableSetOf<String>()
                    multiSelectList?.forEach {
                        val jsonValue = Gson().toJson(it)
                        favoritesSet.add(jsonValue)
                    }
                    activity.saveToPrefs(getTitle(), favoritesSet)
                    activity.toast("Saved " + favoritesSet.size + " to prefs")
                    mActionMode?.finish()
                    return true
                }
                else -> return false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mActionMode = null
            isMultiSelectMode = false
            multiSelectList = mutableListOf<SearchableItem>()
            refreshAdapter()
        }
    }

}