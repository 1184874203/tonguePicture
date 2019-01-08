package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.PreferenceManager
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
            saveDB()
            activity!!.finish()
        })
    }

    override fun initData() {
        preferenceManager = PreferenceManager.getInstance(activity!!)
        setText(preferenceManager.getLastUserName(), edt_name)
        setText(preferenceManager.getLastUserAge(), edt_age)
        setText(preferenceManager.getLastUserSex(), edt_sex)
        setText(preferenceManager.getLastUserOtherString(), edt_otherString)
        var bitmapPath = arguments?.getString(TONGUEPIC)
        var file = File(bitmapPath)
        Glide.with(this).load(file).into(iv_tonguePic)
    }

    override fun onPause() {
        super.onPause()
        saveInfo()
    }

    private fun saveDB() {
        var user = User()
        user.name = "" + edt_name.text
        user.age = Integer.valueOf("" + edt_age.text)
        user.otherString = "" + edt_otherString.text
        user.sex = "" + edt_sex.text
        user.time = System.currentTimeMillis()
        ManagerFactory.getInstance(activity!!).getUserManager().save(user)
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

}