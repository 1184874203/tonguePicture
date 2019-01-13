package com.hali.xiaoyangchun.tonguepicture.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response

abstract class BaseRequestFragment<D> : BaseFragment(), OkGoInterface<D> {

    protected open var TAG = ""
    protected var progressDialog = Dialog(activity, R.style.progress_dialog)

    override fun onSuccess(response: Response<D>, requestCode: Int) {
        hideProgressDialog()
    }

    override fun onError(error: String) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressDialog()
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
        OkGo.getInstance().cancelTag(TAG)
        super.onDestroy()
    }
}