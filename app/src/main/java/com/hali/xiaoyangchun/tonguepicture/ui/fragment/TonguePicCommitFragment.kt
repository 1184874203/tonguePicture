package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.bean.ImageUploadBean
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.PreferenceManager
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenerManager
import com.hali.xiaoyangchun.tonguepicture.model.net.CommonRequest
import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.OkGoInterface
import kotlinx.android.synthetic.main.fragment_tonguepic_commit.*
import java.io.File

class TonguePicCommitFragment : BaseFragment() {
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

    private lateinit var file: File

    override fun getLayoutId() = R.layout.fragment_tonguepic_commit

    override fun initViews() {
        btn_commit = findView(R.id.commit)
        edt_age = findView(R.id.edit_age)
        edt_name = findView(R.id.edit_name)
        edt_sex = findView(R.id.edit_sex)
        edt_otherString = findView(R.id.edit_otherString)
        iv_tonguePic = findView(R.id.tonguePic)
        btn_commit.setOnClickListener({
            saveInfo()
            saveDB({
                ChangeListenerManager.getInstance().notifyDataChanged(ChangeListenerManager.CHANGELISTENERMANAGER_DB_INSERT, it)
            })
            var files = listOf(file)
            upLoadImage(files)
        })
    }

    override fun initData() {
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
        var user = User()
        user.id = System.currentTimeMillis()
        user.name = "" + edt_name.text
        user.age = Integer.valueOf("" + edt_age.text)
        user.otherString = "" + edt_otherString.text
        user.sex = "" + edt_sex.text
        user.time = System.currentTimeMillis()
        ManagerFactory.getInstance(activity!!).getUserManager().save(user)
        action(user)
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

    private fun upLoadImage(files: List<File>) {
        CommonRequest.uploadImage(object : OkGoInterface<ImageUploadBean>{
            override fun onSuccess(response: ImageUploadBean?, requestCode: Int) {
                edit_otherString.setText(response?.url + "")
            }

            override fun onError(error: String) {

            }
        })
    }
}