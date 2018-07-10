package org.weatherook.weatherook.ui.fragment.my


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_friend.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.viewpager.FriendPagerAdapter

/**
 * Created by HYEON on 2018-07-08.
 */
class FriendFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?){

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_friend, container,false)
        return  v
    }

    override fun onStart() {
        super.onStart()

        val friendViewPager = view!!.findViewById<ViewPager>(R.id.friend_viewpager)
        val friendAdapter = FriendPagerAdapter(childFragmentManager)

        friendViewPager.adapter = friendAdapter
        friend_tablayout.setupWithViewPager(friendViewPager)
    }
}