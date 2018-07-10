package org.weatherook.weatherook.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_comment.*
import org.weatherook.weatherook.R
import org.weatherook.weatherook.adapter.recyclerview.CommentTotalAdapter
import org.weatherook.weatherook.item.CommentTotalItem

class CommentActivity : AppCompatActivity() {

    lateinit var commenttotalItems: ArrayList<CommentTotalItem>
    lateinit var commenttotalAdapter : CommentTotalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        commenttotalItems = ArrayList()
        commenttotalItems.clear()

        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))
        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))
        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))
        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))
        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))
        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))
        commenttotalItems.add(CommentTotalItem(R.drawable.main_rain,"지금","13분전","우억"))

        commenttotalAdapter = CommentTotalAdapter(commenttotalItems, applicationContext)

        comment_comment_recycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        comment_comment_recycler.adapter = commenttotalAdapter
    }
}
