package org.weatherook.weatherook.api.model

import com.google.gson.annotations.SerializedName
import org.weatherook.weatherook.item.BoardCommentItem


data class TodayFilterModel(
        @SerializedName("message") val message: String, // Successfully total board filtering
        @SerializedName("data") val data: List<Data>
) {

    data class Data(
            @SerializedName("user_img") val userImg: Any, // null
            @SerializedName("user_id") val userId: String, // appjamidams
            @SerializedName("board_idx") val boardIdx: Int, // 55
            @SerializedName("board_img") val boardImg: String, // https://s3.ap-northeast-2.amazonaws.com/weatherook/3.PNG
            @SerializedName("board_desc") val boardDesc: Any, // null
            @SerializedName("like_cnt") val likeCnt: Int, // 9
            @SerializedName("like_flag") val likeFlag: Int, // 0
            @SerializedName("board_temp_min") val boardTempMin: Int, // 19
            @SerializedName("board_temp_max") val boardTempMax: Int, // 28
            @SerializedName("board_weather") val boardWeather: Int, // 0
            @SerializedName("board_date") val boardDate: String, // 07-01
            @SerializedName("comment_list") val commentList: List<BoardCommentItem>,
            @SerializedName("comment_cnt") val commentCnt: Int, // 3
            @SerializedName("flag") val flag: Int // 1
    ) {

        /*data class Comment(
                @SerializedName("comment_idx") val commentIdx: Int, // 145
                @SerializedName("comment_desc") val commentDesc: String, // 스타일이 독특해서 팔로우했어요
                @SerializedName("comment_date") val commentDate: String, // 2018-07-12T13:58:08.000Z
                @SerializedName("comment_id") val commentId: String // drifin94
        )*/
    }
}