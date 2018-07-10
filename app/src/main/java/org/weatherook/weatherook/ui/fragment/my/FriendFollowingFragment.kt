package org.weatherook.weatherook.ui.fragment.my

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_friend_follower.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.FriendAdapter
import org.weatherook.weatherook.item.FriendItem

/**
 * Created by HYEON on 2018-07-08.
 */
class FriendFollowingFragment : Fragment() {

    lateinit var friendItems : ArrayList<FriendItem>
    lateinit var friendAdapter : FriendAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val v = inflater.inflate(R.layout.fragment_friend_following, container,false)
        return  v
    }

    override fun onStart() {
        super.onStart()

        friendItems = ArrayList()
        friendItems.add(FriendItem(R.drawable.cony,"정빈이"))
        friendItems.add(FriendItem(R.drawable.cony,"ss_2"))
        friendItems.add(FriendItem(R.drawable.cony,"hiriyo_"))
        friendItems.add(FriendItem(R.drawable.cony,"sultag"))
        friendItems.add(FriendItem(R.drawable.cony,"정빈이"))
        friendItems.add(FriendItem(R.drawable.cony,"ㅎㅎ"))
        friendItems.add(FriendItem(R.drawable.cony,"정빈이"))
        friendItems.add(FriendItem(R.drawable.cony,"정빈이"))
        friendItems.add(FriendItem(R.drawable.cony,"ss_2"))
        friendItems.add(FriendItem(R.drawable.cony,"hiriyo_"))
        friendItems.add(FriendItem(R.drawable.cony,"sultag"))
        friendItems.add(FriendItem(R.drawable.cony,"정빈이"))
        friendItems.add(FriendItem(R.drawable.cony,"ㅎㅎ"))
        friendItems.add(FriendItem(R.drawable.cony,"정빈이"))


        friendAdapter = FriendAdapter(friendItems, context!!)
        friend_follower_recycle.layoutManager = GridLayoutManager(context,4)
        friend_follower_recycle.adapter = friendAdapter
    }

}