package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.support.v7.widget.Toolbar
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity
import com.hali.xiaoyangchun.tonguepicture.ui.fragment.FragmentFactory

class SingleFragmentActivity : BaseActivity() {

    companion object {
        val TITLE = "title"
        val TAG = "tag"
    }
    override fun getLayoutId() = R.layout.activity_singlefragment

    override fun initViews() {
        var toolbar = findView<Toolbar>(R.id.common_toolbar)
        var title = intent.getStringExtra(TITLE)
        var tag = intent.getStringExtra(TAG)
        initToolBar(toolbar, title, true)
        var singleFragment = FragmentFactory.createFragmentByTag(tag, intent.extras)
        if (singleFragment != null)
            supportFragmentManager.beginTransaction().add(R.id.content, singleFragment).show(singleFragment).commitNow()
    }

    override fun initData() {

    }
}