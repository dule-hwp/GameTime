package hwp.gametime.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import hwp.gametime.fragments.FavouritePagerItemFragment

/**
 * Created by dusan_cvetkovic on 12/9/16.
 */
class FavoritesPagerAdapter(fm: FragmentManager, val searchableFragment: List<FavouritePagerItemFragment>) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return searchableFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return searchableFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return searchableFragment[position].mTitle
    }
}