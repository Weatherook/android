package org.weatherook.weatherook.ui.fragment.my

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_my.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.ui.activity.MySettingActivity
import org.weatherook.weatherook.ui.activity.SettingsActivity
import org.weatherook.weatherook.ui.fragment.FollowingBFragment

class MyFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {
        /*val idx : Int = mypage_recycle.getAdapterPosition(p0) //몇번째 인지에 대한 정보를 알려줌
        val myimage : Int = myitems[idx].myimage
        var intent : Intent = Intent(activity,MyStoryActivity::class.java)
        intent.putExtra("myimage",myimage)
        requireActivity().startActivity(intent!!)*/

        when (p0) {
            my_grid_img -> {
                /*setting_recycle.layoutManager = GridLayoutManager(activity,2)
                myGridRecyclerviewAdapter = MyGridRecyclerviewAdapter(myitems, context!!)
                myGridRecyclerviewAdapter.setOnItemClickListener(this@MyFragment)
                setting_recycle.adapter = myGridRecyclerviewAdapter*/

                my_grid_img.isSelected = true
                my_list_img.isSelected = false
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.mypage_recycle, MyGridFragment()).commit()
            }

            my_list_img -> {
                my_list_img.isSelected = true
                my_grid_img.isSelected = false
                /*mylist_recycle.layoutManager = LinearLayoutManager(activity)
                myListRecyclerviewAdapter = MyListRecyclerviewAdapter(mylistitems, context!!)
                myListRecyclerviewAdapter.setOnItemClickListener(this@MyFragment)
                mylist_recycle.adapter = myListRecyclerviewAdapter*/
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.mypage_recycle, FollowingBFragment()).commit()
            }
            my_setting_btn -> {
                var intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
            }
            my_profile_edit_btn -> {
                my_profile_edit_btn.isSelected = false
                var intent = Intent(activity, MySettingActivity::class.java)
                startActivity(intent)
            }
            my_follow -> {
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.add(R.id.my_frame, FriendFragment()).commit()
            }


        }

    }

    /*var myitems: ArrayList<MyGridRecyclerviewdata> = ArrayList()
    var mylistitems: ArrayList<MyListRecyclerviewData> = ArrayList()

    lateinit var myGridRecyclerviewAdapter: MyGridRecyclerviewAdapter
    lateinit var myListRecyclerviewAdapter: MyListRecyclerviewAdapter*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = View.inflate(activity, R.layout.fragment_my, null)

        //val mygrid_recycle : RecyclerView = view.findViewById(R.id.setting_grid_recycle)
        //val mylist_recycle : RecyclerView = view.findViewById(R.id.setting_list_recycle)
        //val fragmentManager : FragmentManager = activity!!.supportFragmentManager

              return view
    }

    override fun onStart() {
        super.onStart()


        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.mypage_recycle, MyGridFragment()).commit()
        my_grid_img.isSelected = true
        my_grid_img.setOnClickListener(this)
        my_list_img.setOnClickListener(this)
        my_setting_btn.setOnClickListener(this)
        my_profile_edit_btn.setOnClickListener(this)
        my_follow.setOnClickListener(this)


    }
}