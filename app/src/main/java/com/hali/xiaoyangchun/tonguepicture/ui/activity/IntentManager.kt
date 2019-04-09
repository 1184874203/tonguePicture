package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.CameraFragment
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TonguePicCommitFragment
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TonguePicDetailFragment

object SingleFAHelper {

    fun gotoTonguePicCommitFragment(activity: Activity, tonguePicPath: String) {
        var intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "编辑信息")
        intent.putExtra(SingleFragmentActivity.TAG, TonguePicCommitFragment.TonguePicCommitFragment_TAG)
        intent.putExtra(SingleFragmentActivity.NEEDTOOLBAR, true)
        var bundle = Bundle()
        bundle.putString(TonguePicCommitFragment.TONGUEPIC, tonguePicPath)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    fun gotoCameraFragment(activity: Activity) {
        var intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "")
        intent.putExtra(SingleFragmentActivity.TAG, CameraFragment.CAMERAFRAGMENT_TAG)
        intent.putExtra(SingleFragmentActivity.FULLSCREEN, true)
        activity.startActivity(intent)
    }

    fun gotoTonguePicDetailFragment(activity: Activity) {
        var intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "诊断详情")
        intent.putExtra(SingleFragmentActivity.TAG, TonguePicDetailFragment.TonguePicDetailFragment_TAG)
        intent.putExtra(SingleFragmentActivity.FULLSCREEN, true)
        activity.startActivity(intent)
    }
}