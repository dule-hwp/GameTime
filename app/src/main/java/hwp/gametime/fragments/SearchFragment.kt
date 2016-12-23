package hwp.gametime.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hwp.gametime.R
import hwp.gametime.adapters.SearchPagerAdapter
import kotlinx.android.synthetic.main.fragment_view_pager.*


class SearchFragment private constructor() : Fragment(), SearchView.OnQueryTextListener {

    var mVpAdapter: SearchPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.let {
            setViewPager()
        }
        mCurrFragment = (viewPager.adapter as? SearchPagerAdapter)?.getItem(viewPager.currentItem)
                as? SearchableBaseFragment
    }

    private var tabLayout: TabLayout? = null

    fun setViewPager() {
        val vpFragments = listOf(SearchableTeamsFragment.newInstance(),
                SearchablePlayersFragment.newInstance())
        mVpAdapter = SearchPagerAdapter(activity.supportFragmentManager, vpFragments)
        viewPager?.adapter = mVpAdapter
        viewPager?.addOnPageChangeListener(onPageListener)

        tabLayout = activity.findViewById(R.id.tabs) as? TabLayout
//        tabLayout?.visibility = View.VISIBLE
        tabLayout?.post { tabLayout?.setupWithViewPager(viewPager) }

    }

    private var mCurrFragment: SearchableBaseFragment? = null
    private val onPageListener: ViewPager.SimpleOnPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            val ad = viewPager.adapter as SearchPagerAdapter
            mCurrFragment = ad.getItem(position) as? SearchableBaseFragment
        }
    }

    override fun onResume() {
        super.onResume()
//        tabLayout?.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
//        tabLayout?.visibility = View.GONE

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null)
            mCurrFragment?.performFiltering(newText)
        return true
    }

    companion object {
        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
