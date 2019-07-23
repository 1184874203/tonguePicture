package com.hali.xiaoyangchun.tonguepicture.ui.base

import android.app.Dialog
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.lzy.okgo.OkGo

abstract class BaseRequestFragment : BaseFragment(), OkGoInterface {

    protected open var TAG = ""
    protected lateinit var progressDialog: Dialog

    override fun onSuccess(response: Any?, requestCode: Int) {

    }

    override fun onError(error: String) {

    }

    override fun initViews() {
        progressDialog = Dialog(activity, R.style.progress_dialog)
    }

    fun showProgressDialog() {
        progressDialog.setContentView(R.layout.dialog_progress_layout)
        progressDialog.setCancelable(true)
        progressDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun onCreateRequest() {

    }

    abstract fun getRequestUrl(): String

    override fun onDestroy() {
        OkGo.getInstance().cancelTag(this)
        super.onDestroy()
    }
}