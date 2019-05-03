package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.UserInfo
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenerManager
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

class NickChangeFragment : BaseFragment() {

    companion object {
        val NICKCHANGEFRAGMENT_TAG = "NickChangeFragment_TAG"
    }
    private lateinit var edtNick: EditText
    private lateinit var btnOk: Button

    override fun getLayoutId() = R.layout.fragment_account_change

    override fun initViews() {
        edtNick = findView(R.id.et_name_input)
        btnOk = findView(R.id.btn_ok)
    }

    override fun initData() {
        val oldNick = UserInfo.getInstance(mActivity).nick
        if (!TextUtils.isEmpty(oldNick)) {
            edtNick.setText(oldNick)
        }
        btnOk.setOnClickListener {
            if (TextUtils.isEmpty(edtNick.text)) {
                Toast.makeText(mActivity, "昵称不能为空", Toast.LENGTH_SHORT).show()
            } else {
                UserInfo.getInstance(mActivity).nick = edtNick.text.toString()
                ChangeListenerManager.getInstance().notifyDataChanged("nick_change", edtNick.text.toString())
                Toast.makeText(mActivity, "修改成功!", Toast.LENGTH_SHORT).show()
                mActivity.finish()
            }
        }
    }

}