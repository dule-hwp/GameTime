package hwp.gametime.adapters

import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import hwp.gametime.fragments.SearchableBaseFragment

/**
 * Created by dusan_cvetkovic on 12/9/16.
 */
class SearchPagerAdapter(fm: FragmentManager, val searchableFragment: List<SearchableBaseFragment>) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return searchableFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return searchableFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return searchableFragment.get(position).getTitle()
    }
}