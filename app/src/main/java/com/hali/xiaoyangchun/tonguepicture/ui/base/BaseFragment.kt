package com.hali.xiaoyangchun.tonguepicture.ui.base

import android.app.Activity
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open abstract class BaseFragment : Fragment() {

    protected lateinit var mActivity: Activity

    lateinit var convertView: View
    private var views = SparseArray<View>()

    open abstract fun getLayoutId(): Int
    open abstract fun initViews()
    open abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        convertView = inflater.inflate(getLayoutId(), container, false)
        return convertView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    fun <v: View>findView(viewId: Int): v {
        var view = views.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as v
    }

    override fun onDestroy() {
        super.onDestroy()
        views.clear()
    }
}