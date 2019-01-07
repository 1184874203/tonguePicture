package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.TonguePicCommitFragment

object SingleFAHelper {

    fun gotoTonguePicCommitFragment(activity: Activity, tonguePic: Bitmap) {
        var intent = Intent(activity, SingleFragmentActivity::class.java)
        intent.putExtra(SingleFragmentActivity.TITLE, "编辑信息")
        intent.putExtra(SingleFragmentActivity.TAG, TonguePicCommitFragment.TonguePicCommitFragment_TAG)
        var bundle = Bundle()
//        bundle.putParcelable(TonguePicCommitFragment.TONGUEPIC, tonguePic)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }
}