package org.weatherook.weatherook.ui.fragment.my

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.FriendAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.FriendItem
import org.weatherook.weatherook.singleton.tokenDriver

class FriendFollowerFragment : Fragment(),View.OnClickListener {
    override fun onClick(v: View?) {
        //

    }

    lateinit var friendItems: ArrayList<FriendItem>
    lateinit var friendAdapter: FriendAdapter

    var token: String? = null

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_friend_follower, container, false)
        friendItems = ArrayList()
        friendAdapter = FriendAdapter(friendItems, context!!)
        tokenDriver.tokenDriver.subscribe{
            token = it
            Log.i("follower", token)
        }


        return v
    }

    override fun onStart() {
        super.onStart()

        val rv : RecyclerView = view!!.findViewById(R.id.friend_follower_recycle)
        rv.layoutManager = GridLayoutManager(context, 4)
        rv.adapter = friendAdapter
        if (token != null) {
            val call = networkService.getMyFollowerProfile(token!!)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { success ->
                                Log.i("followerFragmentNum", success.data.showFollowerIDResult.size.toString())
                                for (i in 0..success.data.showFollowerIDResult.size - 1){
                                    friendItems.add(FriendItem(success.data.showFollowerIDResult[i].userImg, success.data.showFollowerIDResult[i].userId,success.data.showFollowerIDResult[i].userDesc.toString()))
                                    Log.i("followerFragment", success.data.showFollowerIDResult[i].userImg)
                                    friendAdapter.notifyDataSetChanged()
                                    Log.i("followerFragment", success.message.toString())
                                }
                            }, { fail -> Log.i("follower_friend", fail.message) })
        }
        friendAdapter.notifyDataSetChanged()
    }
}