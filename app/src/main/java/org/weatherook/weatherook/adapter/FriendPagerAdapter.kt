package org.weatherook.weatherook.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.weatherook.weatherook.ui.fragment.FriendFollowerFragment
import org.weatherook.weatherook.ui.fragment.FriendFollowingFragment


class FriendPagerAdapter  (fm : FragmentManager) : FragmentPagerAdapter(fm)  {

    val imageCount = 2
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FriendFollowingFragment()
            else -> return FriendFollowerFragment()
        }
    }

    override fun getCount(): Int {
        return imageCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "팔로잉"
            else -> {
                return "팔로워"
            }
        }
    }
}