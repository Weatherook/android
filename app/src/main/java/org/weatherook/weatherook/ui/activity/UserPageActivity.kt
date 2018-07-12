package org.weatherook.weatherook.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_my.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.api.glide.GlideApp
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.singleton.tokenDriver

class UserPageActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            my_grid_img -> {
                my_grid_img.isSelected = true
                my_list_img.isSelected = false
                //val fragmentTransaction = fragmentManager.beginTransaction()
                //fragmentTransaction.replace(R.id.mypage_recycle, MyGridFragment()).commit()
            }

            my_list_img -> {
                my_list_img.isSelected = true
                my_grid_img.isSelected = false
                //val fragmentTransaction = fragmentManager!!.beginTransaction()
                //fragmentTransaction.replace(R.id.mypage_recycle, MyListFragment()).commit()

            }
        }
    }

    var token : String ?= null
    var userId : String ?= null


    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

   // var frienditems: ArrayList<FriendItem> = ArrayList()
  //  lateinit var friendAdapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)

        tokenDriver.tokenDriver.subscribe{
            token = it
            Log.i("grid", token)
        }
        userId= intent.getStringExtra("id")


    }

    override fun onStart() {
        super.onStart()

       // val fragmentTransaction = fragmentManager!!.beginTransaction()
        //fragmentTransaction.replace(R.id.mypage_recycle, MyGridFragment()).commit()

        if (token != null) {
            val call = networkService.getMyBoard(token!!,userId!!)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { success ->
                                Log.i("urls_success1", success.showBoardNumResult.toString())
                                GlideApp.with(this).load(success.showUserPageResult[0].userImg).into(setting_profile_img)
                                setting_id_tv.text = success.showUserPageResult[0].userId
                                setting_self_tv.text = success.showUserPageResult[0].userDesc.toString()
                                my_board_num.text = success.showBoardNumResult[0].boardNum.toString()
                                my_follower_num.text = success.showFollowerNumResult[0].follower.toString().toFloat().toInt().toString()/*.toInt().toString()*/
                                Log.i("FollowerNumResult",success.showFollowerNumResult[0].follower.toString())
                                my_following_num.text = success.showFollowingNumResult[0].following.toString()
                            }, { /*fail -> Log.i("urls_failed", fail.message)*/ })
        }

        my_grid_img.setOnClickListener(this)
        my_list_img.setOnClickListener(this)

    }
}
