package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.os.Bundle
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

object FragmentFactory {
    fun createFragmentByTag(tag: String, bundle: Bundle): BaseFragment? {
        when (tag) {
            TonguePicCommitFragment.TonguePicCommitFragment_TAG -> {
                return createTonguePicCommitFragment(bundle)
            }
            CameraFragment.CAMERAFRAGMENT_TAG -> {
                return createCameraFragment(bundle)
            }
            TonguePicDetailFragment.TonguePicDetailFragment_TAG -> {
                return createTonguePicDetailFragment(bundle)
            }
        }
        return null
    }

    fun createTonguePicCommitFragment(bundle: Bundle):BaseFragment? {
        var fragment = TonguePicCommitFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }

    fun createCameraFragment(bundle: Bundle): BaseFragment? {
        var fragment = CameraFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }

    fun createTonguePicDetailFragment(bundle: Bundle): BaseFragment? {
        var fragment = TonguePicDetailFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }
}