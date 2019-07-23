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
            NickChangeFragment.NICKCHANGEFRAGMENT_TAG -> {
                return createNickChangeFragment(bundle)
            }
        }
        return null
    }

    private fun createTonguePicCommitFragment(bundle: Bundle): BaseFragment? {
        val fragment = TonguePicCommitFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }

    private fun createCameraFragment(bundle: Bundle): BaseFragment? {
        val fragment = CameraFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }

    private fun createTonguePicDetailFragment(bundle: Bundle): BaseFragment? {
        var fragment = TonguePicDetailFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }

    private fun createNickChangeFragment(bundle: Bundle): BaseFragment? {
        var fragment = NickChangeFragment()
        if (bundle != null)
            fragment.arguments = bundle
        return fragment
    }
}