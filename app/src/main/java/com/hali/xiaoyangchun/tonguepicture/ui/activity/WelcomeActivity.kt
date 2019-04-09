package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.content.Intent
import android.widget.ImageView
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
        findView<ImageView>(R.id.iv_welcome).animate().alpha(1.0f).scaleX(1.1f).scaleY(1.1f).setDuration(1000).withEndAction {
            try {
                sleep(500)
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
        }.start()
    }

    override fun setFullScreen() = true
}