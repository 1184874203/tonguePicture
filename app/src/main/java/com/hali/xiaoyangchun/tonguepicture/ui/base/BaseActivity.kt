package com.hali.xiaoyangchun.tonguepicture.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

open abstract class BaseActivity : AppCompatActivity() {
    private var views = SparseArray<View>()
    private lateinit var inputManager: InputMethodManager

    open abstract fun getLayoutId(): Int
    open abstract fun initViews()
    open abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (setFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(getLayoutId())
        initViews()
        initData()
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
        toolbar.visibility = View.VISIBLE
        toolbar.setTitle(title)
        setSupportActionBar(toolbar)
        if (isBack) {
            try {
                var actionBar = supportActionBar
                if (null != actionBar) {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (currentFocus != null && currentFocus.windowToken != null) {
                inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onDestroy() {
        super.onDestroy()
        views.clear()
    }
}