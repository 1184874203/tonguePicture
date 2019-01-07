package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.graphics.Bitmap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

class TonguePicCommitFragment : BaseFragment() {
    companion object {
        val TonguePicCommitFragment_TAG = "TonguePicCommitFragment_TAG"
        val TONGUEPIC = "tongue_pic"
    }

    private lateinit var btn_commit: Button
    private lateinit var edt_name: EditText
    private lateinit var edt_age: EditText
    private lateinit var edt_sex: EditText
    private lateinit var iv_tonguePic: ImageView

    override fun getLayoutId() = R.layout.fragment_tonguepic_commit

    override fun initViews() {
        btn_commit = findView(R.id.commit)
        edt_age = findView(R.id.edit_age)
        edt_name = findView(R.id.edit_name)
        edt_sex = findView(R.id.edit_sex)
        iv_tonguePic = findView(R.id.tonguePic)
    }

    override fun initData() {
        var bitmap: Bitmap = arguments?.getParcelable(TONGUEPIC)!!
        iv_tonguePic.setImageBitmap(bitmap)
    }
}