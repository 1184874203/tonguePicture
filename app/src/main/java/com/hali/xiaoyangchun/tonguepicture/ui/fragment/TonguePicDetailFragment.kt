package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.widget.ImageView
import android.widget.TextView
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseRequestFragment

class TonguePicDetailFragment : BaseRequestFragment() {
    override var TAG = "TonguePicDetailFragment"

    private lateinit var tongueImg: ImageView
    private lateinit var name: TextView
    private lateinit var time: TextView
    private lateinit var otherString: TextView
    private lateinit var k1: TextView
    private lateinit var k2: TextView
    private lateinit var k3: TextView
    private lateinit var k4: TextView
    private lateinit var v1: TextView
    private lateinit var v2: TextView
    private lateinit var v3: TextView
    private lateinit var v4: TextView
    companion object {
        val TonguePicDetailFragment_TAG = "TonguePicDetailFragment_TAG"
    }

    override fun getLayoutId() = R.layout.fragment_tongue_detail

    override fun initViews() {
        tongueImg = findView(R.id.iv_tongue)
        name = findView(R.id.tv_name)
        time = findView(R.id.tv_time)
        otherString = findView(R.id.tv_otherString)
        k1 = findView(R.id.tv_p1_k)
        k2 = findView(R.id.tv_p2_k)
        k3 = findView(R.id.tv_p3_k)
        k4 = findView(R.id.tv_p4_k)
        v1 = findView(R.id.tv_p1_v)
        v2 = findView(R.id.tv_p2_v)
        v3 = findView(R.id.tv_p3_v)
        v4 = findView(R.id.tv_p4_v)
    }

    override fun initData() {

    }

    override fun getRequestUrl(): String {
        return ""
    }
}