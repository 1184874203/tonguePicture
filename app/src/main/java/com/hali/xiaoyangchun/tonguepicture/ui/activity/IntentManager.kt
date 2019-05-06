package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.CameraFragment
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.NickChangeFragment
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TonguePicCommitFragment
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TonguePicDetailFragment

object SingleFAHelper {

    fun gotoTonguePicCommitFragment(activity: Activity, tonguePicPath: String) {
        val intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "编辑信息")
        intent.putExtra(SingleFragmentActivity.TAG, TonguePicCommitFragment.TonguePicCommitFragment_TAG)
        intent.putExtra(SingleFragmentActivity.NEEDTOOLBAR, true)
        val bundle = Bundle()
        bundle.putString(TonguePicCommitFragment.TONGUEPIC, tonguePicPath)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    fun gotoCameraFragment(activity: Activity) {
        val intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "")
        intent.putExtra(SingleFragmentActivity.TAG, CameraFragment.CAMERAFRAGMENT_TAG)
        intent.putExtra(SingleFragmentActivity.FULLSCREEN, true)
        activity.startActivity(intent)
    }

    fun gotoTonguePicDetailFragment(activity: Activity, user: User) {
        val intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "诊断详情")
        intent.putExtra(SingleFragmentActivity.TAG, TonguePicDetailFragment.TonguePicDetailFragment_TAG)
        intent.putExtra(SingleFragmentActivity.NEEDTOOLBAR, true)
        intent.putExtra("user", Gson().toJson(user))
        activity.startActivity(intent)
    }

    fun gotoNickChangeFragment(activity: Activity) {
        val intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "")
        intent.putExtra(SingleFragmentActivity.TAG, NickChangeFragment.NICKCHANGEFRAGMENT_TAG)
        intent.putExtra(SingleFragmentActivity.NEEDTOOLBAR, true)
        activity.startActivity(intent)
    }
}