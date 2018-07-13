package org.weatherook.weatherook.ui.fragment

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

/**
 * Created by HYEON on 2018-07-08.
 */
class FriendFollowingFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    lateinit var friendItems : ArrayList<FriendItem>
    lateinit var friendAdapter : FriendAdapter

    var token: String? = null

    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val v = inflater.inflate(R.layout.fragment_friend_following, container,false)
        tokenDriver.tokenDriver.subscribe{
            token = it
            Log.i("following", token)
        }

        return  v
    }

    override fun onStart() {
        super.onStart()
        friendItems = ArrayList()
        friendAdapter = FriendAdapter(friendItems, context!!)
        //friendAdapter.setOnItemClickListener(this)
        Log.i("followingFragmentc", "oncreateview")
        val friend_following_rv : RecyclerView = view!!.findViewById(R.id.friend_following_recycle)
        friend_following_rv.layoutManager = GridLayoutManager(context,4)
        friend_following_rv.adapter = friendAdapter
        if (token != null) {
            val call = networkService.getMyFollowingProfile(token!!)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { success ->
                                for(i in 0..success.data.showFollowingIDResult.size-1){
                                    friendItems.add(FriendItem(success.data.showFollowingIDResult[i].userImg,success.data.showFollowingIDResult[i].userId,success.data.showFollowingIDResult[i].userDesc.toString()))
                                    Log.i("following_success",success.data.showFollowingIDResult[i].userImg)
                                    friendAdapter.notifyDataSetChanged()
                                }
                                Log.i("followingFragment", success.message.toString())
                            }, { fail -> Log.i("following_failed", fail.message) })
        }
        friendAdapter.notifyDataSetChanged()

    }
}