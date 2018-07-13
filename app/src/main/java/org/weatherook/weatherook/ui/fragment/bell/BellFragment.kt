package org.weatherook.weatherook.ui.fragment.bell

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bell.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.BellRecyclerviewAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.BellRecyclerViewData
import org.weatherook.weatherook.singleton.tokenDriver

class BellFragment : Fragment(), View.OnClickListener {
    var token: String? = null


    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    lateinit var likeitems: ArrayList<BellRecyclerViewData>
    lateinit var bellRecyclerviewAdapter: BellRecyclerviewAdapter

    var flag = -1
    override fun onClick(p0: View?) {

        val idx : Int = like_recycle.getChildAdapterPosition(p0)
     /*   bellModel.data[i].flag
        likeitems[idx].fla
        val image : String = likeitems[idx].bellprofile!!
        val id : String = likeitems[idx].bellname
        val heartcount : Int = likeitems[idx].bell
        val photo : String = likeitems[idx].bellboardimg
*/
//0 : 댓글 1: 팔로우
        if(likeitems[idx].bellflag == 0){
            Toast.makeText(context, "댓글", Toast.LENGTH_LONG).show()

        }
        else {
            Toast.makeText(context, "팔로우", Toast.LENGTH_LONG).show()
        }
        //Toast.makeText(context, "ok", Toast.LENGTH_LONG).show()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = View.inflate(activity, R.layout.fragment_bell, null)
        val like_recycle: RecyclerView = view!!.findViewById(R.id.like_recycle)

        tokenDriver.tokenDriver.subscribe {
            token = it
            Log.i("list", token)
        }


//          likeitems.add(BellRecyclerViewData(R.drawable.main_night_2, "유클라", "님이 댓글을 남겼습니다.", "오늘 너무 더워요오", "14", R.drawable.brown))
//          likeitems.add(BellRecyclerViewData(R.drawable.main_snow_2, "프린스정", "님이 댓글을 남겼습니다.", "오늘 너무 더워요오", "15", R.drawable.brown))
//         likeitems.add(BellRecyclerViewData(R.drawable.main_cloud_sun_2, "유클라", "님이 댓글을 남겼습니다.", "오늘 너무 더워요오", "220", R.drawable.brown))
//

        return view
    }

    override fun onStart() {
        super.onStart()



        if (token != null) {
            val call = networkService.getBell(token!!)
            disposable = call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            { bellModel ->
                                Log.i("urls_success1", bellModel.data.toString())
                                likeitems = ArrayList()
                                bellRecyclerviewAdapter = BellRecyclerviewAdapter(likeitems, context!!)
                                bellRecyclerviewAdapter.setOnItemClickListener(this)
                                like_recycle.adapter = bellRecyclerviewAdapter
                                like_recycle.layoutManager = LinearLayoutManager(activity)
                                for (i in 0..bellModel.data.size - 1) {

                                    if (bellModel.data[i].flag == 0) {
                                        var list = bellModel.data[i].dateModify.split(" ")
                                        likeitems.add(BellRecyclerViewData(bellModel.data[i].commentImg,
                                                bellModel.data[i].commentId, bellModel.data[i].commentStr,
                                                bellModel.data[i].commentDesc, list[1], bellModel.data[i].boardImg,bellModel.data[i].flag))
                                        bellRecyclerviewAdapter.notifyDataSetChanged()
                                    }
                                    if (bellModel.data[i].flag == 1) {
                                        var list = bellModel.data[i].dateModify.split(" ")
                                        Log.d("sssssssssss",list[1])
                                        likeitems.add(BellRecyclerViewData(bellModel.data[i].followImg.toString(),
                                                bellModel.data[i].follow, bellModel.data[i].followStr,
                                                "팔로우 하시겠습니까?", list[1], "R.drawable.follow_state",bellModel.data[i].flag))
                                        bellRecyclerviewAdapter.notifyDataSetChanged()
                                        Log.i("urls_success3",bellModel.data.toString())
                                    } else
                                        Log.i("urls_success2", bellModel.data[i].boardImg)
                                }
                                Log.i("urls_success4", "" + likeitems.size)
                                // bellRecyclerviewAdapter.notifyDataSetChanged()

                            }, { /*fail -> Log.i("urls_failed", fail.message)*/ })
        }



    }
}