package com.hali.xiaoyangchun.tonguepicture.camera.presenter

import android.content.Context
import android.graphics.Bitmap
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.camera.interfaces.CameraView
import com.hali.xiaoyangchun.tonguepicture.camera.widgets.PictureDialog

class CameraPresenter(context: Context) {
    private var cameraView: CameraView? = null
    private lateinit var picDailog: PictureDialog

    init {
        picDailog = PictureDialog(context, R.layout.picture_dialog_layout, R.style.DialogTheme)
        picDailog.setDialogListener(object : PictureDialog.onPicDialogClick {
            override fun onUpLoadClick() {

            }

            override fun onCancelClick() {
                picDailog.dismissPicDailog()
            }
        })
    }

    fun attachView(cameraView: CameraView) {
        this.cameraView = cameraView
        this.cameraView!!.onResume()
    }

    fun detachView() {
        this.cameraView!!.onPause()
        this.cameraView = null
    }

    fun isViewAttached() = cameraView != null

    fun switchCameraFacing() {
        if (isViewAttached()) {
            cameraView!!.switchCameraFacing()
        }
    }

    fun takePicture() {
        cameraView!!.takePicture(object : CameraView.TakePictureCallback {
            override fun onFinish(bitmap: Bitmap) {
                picDailog.setPicture(bitmap)
                picDailog.showPicDialog()
            }
        })
    }

}