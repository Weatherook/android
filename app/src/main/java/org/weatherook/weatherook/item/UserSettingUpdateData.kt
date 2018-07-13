package org.weatherook.weatherook.item

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class UserSettingUpdateData (
        val user_desc : String,
        val user_gender : String,
        val user_age : Int,
        val user_img : RequestBody?,
        val user_height : Int,
        val user_weight : Int,
        val user_stylelist : ArrayList<String>
)