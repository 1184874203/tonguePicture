package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.bean.User
import com.hali.xiaoyangchun.tonguepicture.dao.Manager.ManagerFactory
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenerManager
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenr
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFAHelper
import com.hali.xiaoyangchun.tonguepicture.ui.adapter.TongNoteListAdapter
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseFragment

class TongueNoteListFragment : BaseFragment(), ChangeListenr {
    private lateinit var rv_list: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var dataList: MutableList<User>
    private lateinit var adapter: TongNoteListAdapter

    override fun getLayoutId() = R.layout.fragment_notelist

    override fun initViews() {
        rv_list = findView(R.id.rv_list_main)
        fab = findView(R.id.fab_main)
        ChangeListenerManager.getInstance().registerListener(ChangeListenerManager.CHANGELISTENERMANAGER_DB_INSERT, this)
    }

    override fun initData() {
        fab.setOnClickListener({
            SingleFAHelper.gotoCameraFragment(activity!!)
        })
        var layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager
        dataList = ManagerFactory.getInstance(activity!!).getUserManager().queryAll()
        adapter = TongNoteListAdapter(activity!!, dataList)
        rv_list.adapter = adapter
        initListener()
    }

    fun initListener() {
        adapter.itemClickListener = object : TongNoteListAdapter.ItemClickListener{
            override fun onItemClick(view: View, user: User) {
                SingleFAHelper.gotoTonguePicDetailFragment(activity!!)
            }
        }

        adapter.itemLongClickListener = object : TongNoteListAdapter.ItemLongClickListener {
            override fun onItemLongClick(view: View, user: User) {
                var builder = AlertDialog.Builder(activity)
                builder.setTitle("提示")
                builder.setMessage("确定删除该条记录？")
                builder.setCancelable(false)
                builder.setPositiveButton("确定", {dialog, which ->
                    ManagerFactory.getInstance(activity!!).getUserManager().delete(user)
                    dataList.remove(user)
                    adapter?.notifyDataSetChanged()
                })
                builder.setNegativeButton("取消", null)
                builder.create().show()
            }
        }
    }

    override fun onChange(key: String, data: Any) {
        if (key.equals(ChangeListenerManager.CHANGELISTENERMANAGER_DB_INSERT)) {
            if (data is User) {
                dataList.add(0, data)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        ChangeListenerManager.getInstance().unregisterListener(this)
        super.onDestroy()
    }
}
