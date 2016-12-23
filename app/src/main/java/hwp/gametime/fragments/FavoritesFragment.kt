package hwp.gametime.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hwp.gametime.R
import hwp.gametime.adapters.FavoritesPagerAdapter
import kotlinx.android.synthetic.main.fragment_view_pager.*


class FavoritesFragment private constructor() : Fragment() {

    var mVpAdapter: FavoritesPagerAdapter? = null

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
    }

    private var tabLayout: TabLayout? = null

    fun setViewPager() {
        val vpFragments = listOf(FavouritePagerItemFragment(getString(R.string.teams)),
                FavouritePagerItemFragment(getString(R.string.players)))
        mVpAdapter = FavoritesPagerAdapter(activity.supportFragmentManager, vpFragments)
        viewPager?.adapter = mVpAdapter

//        viewPager?.addOnPageChangeListener()

        tabLayout = activity.findViewById(R.id.tabs) as? TabLayout
        tabLayout?.post { tabLayout?.setupWithViewPager(viewPager) }
    }

    override fun onResume() {
        super.onResume()
//        tabLayout?.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
//        tabLayout?.visibility = View.GONE

    }

    companion object {
        fun newInstance(): FavoritesFragment {
            val fragment = FavoritesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
