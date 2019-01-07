package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.os.Bundle
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

object FragmentFactory {
    fun createFragmentByTag(tag: String, bundle: Bundle): BaseFragment? {
        when (tag) {
            TonguePicCommitFragment.TonguePicCommitFragment_TAG -> {
                return CreateTonguePicCommitFragment(bundle)
            }
        }
        return null
    }

    fun CreateTonguePicCommitFragment(bundle: Bundle):BaseFragment? {
        var fragment = TonguePicCommitFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }
}