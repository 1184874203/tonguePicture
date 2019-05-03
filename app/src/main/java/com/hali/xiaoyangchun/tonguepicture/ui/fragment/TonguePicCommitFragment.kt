package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.app.Dialog
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.PreferenceManager
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenerManager
import com.hali.xiaoyangchun.tonguepicture.model.net.CommonRequest
import com.hali.xiaoyangchun.tonguepicture.model.net.RequestConstant
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tonguepic_commit.*
import java.io.File

class TonguePicCommitFragment : BaseFragment(), OkGoInterface {
    companion object {
        val TonguePicCommitFragment_TAG = "TonguePicCommitFragment_TAG"
        val TONGUEPIC = "tongue_pic"
    }

    private lateinit var btn_commit: Button
    private lateinit var edt_name: EditText
    private lateinit var edt_age: EditText
    private lateinit var edt_sex: EditText
    private lateinit var edt_otherString: EditText
    private lateinit var iv_tonguePic: ImageView

    private lateinit var preferenceManager: PreferenceManager

    protected lateinit var progressDialog: Dialog

    private lateinit var file: File

    private var upLoadImgUrl = ""

    override fun getLayoutId() = R.layout.fragment_tonguepic_commit

    override fun initViews() {
        btn_commit = findView(R.id.commit)
        edt_age = findView(R.id.edit_age)
        edt_name = findView(R.id.edit_name)
        edt_sex = findView(R.id.edit_sex)
        edt_otherString = findView(R.id.edit_otherString)
        iv_tonguePic = findView(R.id.tonguePic)
        btn_commit.setOnClickListener {
            saveInfo()
            upLoadImage(file)
        }
    }

    override fun onSuccess(response: Any?, requestCode: Int) {
        if (response == null) return
        when (requestCode) {
            RequestConstant.REQUESTCODE_UPLOADIMAGE -> {
                upLoadImgUrl = response.toString()
                if (!TextUtils.isEmpty(upLoadImgUrl)) {
                    CommonRequest.getTongueDetial(activity!!, response.toString(), this)
                } else {
                    Toast.makeText(activity!!, "图片上传失败!", Toast.LENGTH_SHORT).show()
                    hideProgressDialog()
                }
            }
            RequestConstant.REQUESTCODE_TONGUEPICTURE_DETIAL -> {
                Log.i(CommonRequest.TAG, "诊断结果：${response.toString()}")
                hideProgressDialog()
                saveDB {
                    ChangeListenerManager.getInstance()
                            .notifyDataChanged(ChangeListenerManager.CHANGELISTENERMANAGER_DB_INSERT, it)
                }
            }
        }
    }

    override fun onError(error: String) {
        Toast.makeText(activity!!, "上传失败", Toast.LENGTH_SHORT).show()
        hideProgressDialog()
    }

    fun showProgressDialog() {
        progressDialog.setContentView(R.layout.dialog_progress_layout)
        progressDialog.setCancelable(false)
        progressDialog.findViewById<TextView>(R.id.tv_progressmsg).setText("上传中")
        progressDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun initData() {
        progressDialog = Dialog(activity, R.style.progress_dialog)
        preferenceManager = PreferenceManager.getInstance(activity!!)
        setText(preferenceManager.getLastUserName(), edt_name)
        setText(preferenceManager.getLastUserAge(), edt_age)
        setText(preferenceManager.getLastUserSex(), edt_sex)
        setText(preferenceManager.getLastUserOtherString(), edt_otherString)
        var bitmapPath = arguments?.getString(TONGUEPIC)
        file = File(bitmapPath)
        Glide.with(this).load(file).into(iv_tonguePic)
    }

    override fun onPause() {
        super.onPause()
        saveInfo()
    }

    private fun saveDB(action: (user: User) -> Unit) {
        var user = getEditUser()
        user.picPath = upLoadImgUrl
        ManagerFactory.getInstance(activity!!).getUserManager().save(user)
        action(user)
    }

    private fun getEditUser(): User {
        var user = User()
        user.id = System.currentTimeMillis()
        user.name = "" + edt_name.text
        user.age = Integer.valueOf("" + edt_age.text)
        user.otherString = "" + edt_otherString.text
        user.sex = "" + edt_sex.text
        user.time = System.currentTimeMillis()
        return user
    }

    private fun saveInfo() {
        preferenceManager.setLastUserName("" + edt_name.text)
        preferenceManager.setLastUserAge("" + edit_age.text)
        preferenceManager.setLastUserSex("" + edit_sex.text)
        preferenceManager.setLastUserOtherString("" + edit_otherString.text)
    }

    private fun setText(text: String, edt: EditText) {
        if (!text.equals("")) {
            edt.setText(text)
        }
    }

    private fun upLoadImage(file: File) {
        CommonRequest.upLoadImage(context!!, null, file, this)
        showProgressDialog()
    }
}