package com.hali.xiaoyangchun.tonguepicture.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.View
import android.view.WindowManager

open abstract class BaseActivity : AppCompatActivity() {
    private var views = SparseArray<View>()

    open abstract fun getLayoutId(): Int
    open abstract fun initViews()
    open abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (setFullScreen())
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(getLayoutId())
        initViews()
        initData()
    }

    fun <v: View>findView(viewId: Int): v {
        var view = views.get(viewId)
        if (view == null) {
            view = findViewById(viewId)
            views.put(viewId, view)
        }
        return view as v
    }

    open fun setFullScreen(): Boolean = false

    fun initToolBar(toolbar: Toolbar, title: String, isBack: Boolean) {
        toolbar.setTitle(title)
        setSupportActionBar(toolbar)
        if (isBack) {
            try {
                var actionBar = supportActionBar
                if (null != actionBar) {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
            } catch (e: Exception) {
                toolbar.setNavigationOnClickListener { onBackPressed() }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        views.clear()
    }
}