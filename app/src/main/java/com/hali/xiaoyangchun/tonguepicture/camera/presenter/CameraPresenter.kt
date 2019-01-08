package com.hali.xiaoyangchun.tonguepicture.camera.presenter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.camera.interfaces.CameraView
import com.hali.xiaoyangchun.tonguepicture.camera.widgets.PictureDialog
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFAHelper
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFragmentActivity
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TonguePicCommitFragment
import com.hali.xiaoyangchun.tonguepicture.utils.FileUtil

class CameraPresenter(private var context: Activity) {
    private var cameraView: CameraView? = null
    private lateinit var picDailog: PictureDialog

    init {
        picDailog = PictureDialog(context, R.layout.dialog_picdialog_layout, R.style.DialogTheme)
        picDailog.setDialogListener(object : PictureDialog.onPicDialogClick {
            override fun onUpLoadClick() {
                var picPath = FileUtil.savePicture(picDailog.bitmap)
                SingleFAHelper.gotoTonguePicCommitFragment(context, picPath!!)
                picDailog.dismissPicDailog()
                context.finish()
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

    fun saveInDB(user: User) {
        ManagerFactory.getInstance(context).getUserManager().save(user)
    }
}