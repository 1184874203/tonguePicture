package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.widget.Button
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.camera.presenter.CameraPresenter
import com.hali.xiaoyangchun.tonguepicture.camera.widgets.Camera1PreView
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

class CameraFragment : BaseFragment() {

    companion object {
        val CAMERAFRAGMENT_TAG = "CameraFragment_TAG"
    }
    private lateinit var cameraPresenter: CameraPresenter
    private lateinit var camera1: Camera1PreView
    override fun getLayoutId() = R.layout.fragment_camera

    override fun initViews() {
       cameraPresenter = CameraPresenter(activity!!)
        camera1 = findView(R.id.sfv_camera1)
    }

    override fun initData() {
        findView<Button>(R.id.btn_take_picture).setOnClickListener({
            cameraPresenter.takePicture()
        })
    }

    override fun onResume() {
        super.onResume()
        cameraPresenter.attachView(camera1)
    }

    override fun onPause() {
        super.onPause()
        cameraPresenter.detachView()
    }
}