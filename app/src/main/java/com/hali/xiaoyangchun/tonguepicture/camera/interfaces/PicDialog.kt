package com.hali.xiaoyangchun.tonguepicture.camera.interfaces

import android.graphics.Bitmap

interface PicDailog {
    fun showPicDialog()
    fun dismissPicDailog()
    fun setPicture(bitmap: Bitmap)
}