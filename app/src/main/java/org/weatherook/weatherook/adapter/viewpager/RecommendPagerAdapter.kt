package org.weatherook.weatherook.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import org.weatherook.weatherook.ui.fragment.RecommendAFragment
import org.weatherook.weatherook.ui.fragment.RecommendBFragment

class RecommendPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {

    val imageCount = 2
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return RecommendAFragment()
            else -> return RecommendBFragment()
        }
    }

    override fun getCount(): Int {
        return imageCount
    }


}