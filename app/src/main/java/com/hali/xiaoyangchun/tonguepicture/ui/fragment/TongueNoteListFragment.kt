package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFAHelper
import com.hali.xiaoyangchun.tonguepicture.ui.adapter.TongNoteListAdapter
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

class TongueNoteListFragment : BaseFragment() {
    private lateinit var rv_list: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun getLayoutId() = R.layout.fragment_notelist

    override fun initViews() {
        rv_list = findView(R.id.rv_list_main)
        fab = findView(R.id.fab_main)
    }

    override fun initData() {
        fab.setOnClickListener({
            SingleFAHelper.gotoCameraFragment(activity!!)
        })
        var layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager
        var data = ManagerFactory.getInstance(activity!!).getUserManager().queryAll()
        var adapter = TongNoteListAdapter(activity!!, data)
        rv_list.adapter = adapter
    }
}
