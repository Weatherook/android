package org.weatherook.weatherook.ui.fragment.my


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_friend.*
import kotlinx.android.synthetic.main.fragment_friend.view.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.viewpager.FriendPagerAdapter

/**
 * Created by HYEON on 2018-07-08.
 */
class FriendFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?){

        when(p0){
            friend_back-> {
                val friendViewPager = view!!.findViewById<ViewPager>(R.id.friend_viewpager)
                friendViewPager.adapter=null
                activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
                Log.d("444444444444444444","4444444444444444444444444444")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v :View = inflater.inflate(R.layout.fragment_friend, container,false)

        return  v
    }

    override fun onStart() {
        super.onStart()
        friend_back.setOnClickListener(this)
        val friendViewPager = view!!.findViewById<ViewPager>(R.id.friend_viewpager)
        val friendAdapter = FriendPagerAdapter(fragmentManager!!)

        friendViewPager.adapter = friendAdapter
        view!!.friend_tablayout.setupWithViewPager(friendViewPager)



    }
}