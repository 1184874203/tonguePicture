package com.hali.xiaoyangchun.tonguepicture.presenter

import android.app.Activity
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.camera.CameraView
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.interfaces.CameraAction
import com.hali.xiaoyangchun.tonguepicture.utils.FileUtil


class CameraPresenter(private var context: Activity) : CameraView.Callback(){
    private var mCameraView: CameraView? = null

    fun attachView(cameraView: CameraView) {
        cameraView.addCallback(this)
        this.mCameraView = cameraView
        this.mCameraView?.start()
    }

    fun detachView() {
        mCameraView?.stop()
        this.mCameraView = null
    }

    fun isViewAttached() = mCameraView != null

    fun switchCameraFacing() {
        if (isViewAttached()) {
            var facing = mCameraView?.facing
            mCameraView?.facing = if (facing == CameraView.FACING_FRONT) CameraView.FACING_BACK else CameraView.FACING_FRONT
        }
    }

    fun takePicture() {
        mCameraView?.takePicture()
    }

    override fun onPictureTaken(cameraView: CameraView?, data: ByteArray?) {
        mCameraAction?.onPictureTaken(cameraView, data)
    }

    var mCameraAction: CameraAction? = null

    fun saveInDB(user: User) {
        ManagerFactory.getInstance(context).getUserManager().save(user)
    }
}