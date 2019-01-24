package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.content.Intent
import com.hali.xiaoyangchun.tonguepicture.MainActivity
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.PreferenceManager
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity
import java.lang.Thread.sleep

class WelcomeActivity : BaseActivity() {

    private var isFirst = false

    override fun getLayoutId() = R.layout.activity_welcome

    override fun initViews() {

    }

    override fun initData() {
        Thread({
            try {
                sleep(1500)
                isFirst = PreferenceManager.getInstance(this).isUserFirstInit()
                if (isFirst) {
                    var intent = Intent(this, GuidePageActivity::class.java)
                    startActivity(intent)
                } else {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                finish()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()
    }

    override fun setFullScreen() = true
}