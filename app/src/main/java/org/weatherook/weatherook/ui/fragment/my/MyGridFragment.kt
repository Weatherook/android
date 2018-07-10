package org.weatherook.weatherook.ui.fragment.my

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.MyGridRecyclerviewAdapter
import org.weatherook.weatherook.item.MyGridRecyclerviewdata


/**
 * Created by HYEON on 2018-07-04.
 */
class MyGridFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {

    }
    var myitems: ArrayList<MyGridRecyclerviewdata> = ArrayList()

    lateinit var myGridRecyclerviewAdapter: MyGridRecyclerviewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity, R.layout.fragment_my_grid, null)
        return view
    }

    override fun onStart() {
        super.onStart()

        val mypage_recycle: RecyclerView = view!!.findViewById(R.id.setting_grid_recycle)

        myitems = ArrayList()
        myitems.add(MyGridRecyclerviewdata(R.drawable.brown))
        myitems.add(MyGridRecyclerviewdata(R.drawable.brown))
        myitems.add(MyGridRecyclerviewdata(R.drawable.brown))
        myitems.add(MyGridRecyclerviewdata(R.drawable.brown))
        myitems.add(MyGridRecyclerviewdata(R.drawable.brown))
        myitems.add(MyGridRecyclerviewdata(R.drawable.brown))

        myGridRecyclerviewAdapter = MyGridRecyclerviewAdapter(myitems, context!!)
        myGridRecyclerviewAdapter.setOnItemClickListener(this)
        mypage_recycle.layoutManager = GridLayoutManager(activity, 2)
        //myGridRecyclerviewAdapter = MyGridRecyclerviewAdapter(myitems, context!!)
        mypage_recycle.adapter = myGridRecyclerviewAdapter

    }
}