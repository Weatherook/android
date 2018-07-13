package org.weatherook.weatherook.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_comment.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.CommentTotalAdapter
import org.weatherook.weatherook.api.network.NetworkService
import org.weatherook.weatherook.item.BoardCommentItem
import org.weatherook.weatherook.item.CommentTotalItem
import org.weatherook.weatherook.singleton.tokenDriver

class CommentActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v)
        {
            reply_complete_btn -> {
                Comment()
            }
        }
    }

    var token :String? =null
    val networkService by lazy {
        NetworkService.create()
    }
    var disposable: Disposable? = null

    lateinit var commenttotalItems: ArrayList<CommentTotalItem>
    lateinit var commenttotalAdapter : CommentTotalAdapter
    var boardIdx : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        tokenDriver.tokenDriver.subscribe{
            token = it
            Log.i("grid", token)
        }

        reply_complete_btn.setOnClickListener(this)
        commenttotalItems = ArrayList()
        //commenttotalItems = Gson().fromJson(intent.getStringExtra("comment"))
        commenttotalItems.clear()
        //commenttotalItems = fromJsonString(intent.getStringExtra("comment"))
        boardIdx = intent.getIntExtra("boardIdx",0)


        //val comment_profile = intent.getIntExtra("user_profile",0)
        //comment_profile
       // val comment_id = intent.getStringExtra("user_id")
       // comment_id.toString()
      //  comment_id.text
       // val profile : Int = intent.getIntExtra("user_profile",0)
       // comment_profile.setImageResource(profile)
        Glide.with(this).load(intent.getStringExtra("user_profile")).into(comment_profile)
        comment_id.text = intent.getStringExtra("user_id")
        comment_count.text = intent.getStringExtra("like_count")
        reply_date.text = intent.getStringExtra("date")
        reply_weather.text = intent.getStringExtra("weather")
        reply_temp.text = intent.getStringExtra("temperature")


        total()
        reply_close.setOnClickListener{
            finish()
        }
//        reply_menu.setOnClickListener {
//            val view = LayoutInflater.from(applicationContext).inflate(R.layout.bottom_sheet, null)
//            val dialog = BottomSheetDialog(this)
//            dialog.setContentView(view)
//            dialog.show()
//        }
    }

    fun total(){

        val call = networkService.getOneBoardComment(token!!,boardIdx)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { GetCommentModel ->
                            Log.i("latestboard", GetCommentModel.data.size.toString())
                            Glide.with(this).load(GetCommentModel.userImg).into(reply_write_profile)
                            commenttotalItems.clear()
                            for (i in 0..GetCommentModel.data.size-1){
                                var list = GetCommentModel.data[i].commentDate.split(" ")
                                GetCommentModel.data[i].let {
                                    commenttotalItems.add(CommentTotalItem(it.userImg,list[2]+":"+list[3]+" 게시",it.commentId,it.commentDesc))
                                    commenttotalAdapter.notifyDataSetChanged()
                                }
                            }
                        }, {/* fail -> Log.i("urls_failed", fail.message) */})


        commenttotalAdapter = CommentTotalAdapter(commenttotalItems, applicationContext)
        comment_comment_recycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        comment_comment_recycler.adapter = commenttotalAdapter
    }
    fun Comment() {
        val board_idx = boardIdx
        val comment_desc = reply_comment_write.text.toString()

        val call = networkService.postcomment(token!!,board_idx,comment_desc)
        disposable = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({success->
                    total()
                    reply_comment_write.setText("")
                   // commenttotalAdapter.notifyDataSetChanged()
                },{fail->
                    Log.d("commenttttt","실패에ㅔ에에")
                })
    }


    fun fromJsonString(s: String): ArrayList<BoardCommentItem> {
        return Gson().fromJson(s, object : TypeToken<ArrayList<BoardCommentItem>>() {}.type);
    }

    override fun onResume() {
        super.onResume()
    }
}
