package com.hali.xiaoyangchun.tonguepicture.ui.activity

import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.hali.xiaoyangchun.tonguepicture.MainActivity
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.PreferenceManager
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity

class GuidePageActivity : BaseActivity(), ViewPager.OnPageChangeListener, View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_guide_start -> {
                PreferenceManager.getInstance(this).setUserFirstInited()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onPageScrollStateChanged(p0: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {
        if (p0 == imageIdArray.size - 1) {
            startBtn.visibility = View.VISIBLE
        } else {
            startBtn.visibility = View.GONE
        }
    }

    private lateinit var vp: ViewPager
    private lateinit var imageIdArray: Array<Int>
    private lateinit var viewList: ArrayList<View>
    private lateinit var startBtn: ImageView

    override fun getLayoutId() = R.layout.activity_guide

    override fun initViews() {
        startBtn = findView(R.id.btn_guide_start)
        initViewPager()
    }

    private fun initViewPager() {
        vp = findView(R.id.vp_guide)
        imageIdArray = arrayOf(R.drawable.guide_one, R.drawable.guide_two, R.drawable.guide_three)
        viewList = ArrayList()
        var params = LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        for (i in 0 until imageIdArray.size) {
            var imageView = ImageView(this)
            imageView.layoutParams = params
            imageView.setBackgroundResource(imageIdArray[i])
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.adjustViewBounds = true
            viewList.add(imageView)
        }
        vp.adapter = GuidePageAdapter(viewList)
        vp.addOnPageChangeListener(this)
    }

    override fun initData() {
        startBtn.setOnClickListener(this)
    }

    class GuidePageAdapter(private var viewList: ArrayList<View>) : PagerAdapter() {
        override fun getCount() = viewList.size

        override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(viewList[position])
            return viewList[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(viewList[position])
        }
    }

    override fun setFullScreen() = true
}