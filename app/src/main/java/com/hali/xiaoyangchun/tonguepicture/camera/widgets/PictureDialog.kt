package com.hali.xiaoyangchun.tonguepicture.camera.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.camera.interfaces.PicDailog

class PictureDialog : Dialog, PicDailog, View.OnClickListener {
    private val TAG = javaClass.simpleName
    var bitmap: Bitmap? = null
    lateinit var picture: ImageView
    constructor(context: Context, layout: Int, style: Int): super(context, style) {
        setContentView(layout)
        var params = window.attributes
        params.gravity = Gravity.CENTER
        window.attributes = params
        findViewById<Button>(R.id.ok).setOnClickListener(this)
        findViewById<Button>(R.id.cancel).setOnClickListener(this)
        picture = findViewById(R.id.picture)
    }

    override fun showPicDialog() {
        if (bitmap == null) {
            Log.e(TAG, "error bitmap cannot be null...")
            return
        }
        this.show()
    }

    override fun dismissPicDailog() {
        bitmap?.recycle()
        bitmap = null
        this.dismiss()
    }

    override fun setPicture(bitmap: Bitmap) {
        this.bitmap = bitmap
        picture.setImageBitmap(bitmap)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.ok -> picDialogClick?.onUpLoadClick()
            R.id.cancel -> picDialogClick?.onCancelClick()
        }
    }

    var picDialogClick: onPicDialogClick? = null
    fun setDialogListener(listener: onPicDialogClick) {
        picDialogClick = listener
    }
    interface onPicDialogClick {
        fun onUpLoadClick()
        fun onCancelClick()
    }
}
