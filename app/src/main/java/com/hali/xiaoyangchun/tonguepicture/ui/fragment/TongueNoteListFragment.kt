package com.hali.xiaoyangchun.tonguepicture.ui.fragment

import android.app.Activity
import android.app.AlertDialog
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
import com.tonguePicture.support.Permission.PermissionHelper
import com.tonguePicture.support.Permission.PermissionRequestListener

class TongueNoteListFragment : BaseFragment(), ChangeListenr {
    private lateinit var rv_list: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var dataList: MutableList<User>
    private lateinit var adapter: TongNoteListAdapter
    private var lastClickItem = -1

    override fun getLayoutId() = R.layout.fragment_notelist

    override fun initViews() {
        rv_list = findView(R.id.rv_list_main)
        fab = findView(R.id.fab_main)
        ChangeListenerManager.getInstance().registerListener(ChangeListenerManager.CHANGELISTENERMANAGER_DB_INSERT, this)
        ChangeListenerManager.getInstance().registerListener(ChangeListenerManager.CHANGELISTENERMANAGER_DB_READ, this)
    }

    override fun initData() {
        fab.setOnClickListener {
            if (PermissionHelper.hasCameraPermissions(mActivity)) {
                SingleFAHelper.gotoCameraFragment(activity!!)
            } else {
                PermissionHelper.requestCameraPermission(mActivity, object : PermissionRequestListener{
                    override fun onGranted(context: Activity?, requestCode: Int) {
                        SingleFAHelper.gotoCameraFragment(activity!!)
                    }

                    override fun onAsked(context: Activity?, requestCode: Int) {}

                    override fun onDenied(context: Activity?, requestCode: Int) {}

                })
            }
        }
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = layoutManager
        dataList = ManagerFactory.getInstance(activity!!).getUserManager().queryAll()
        adapter = TongNoteListAdapter(activity!!, dataList)
        rv_list.adapter = adapter
        initListener()
    }

    private fun initListener() {
        adapter.itemClickListener = object : TongNoteListAdapter.ItemClickListener {
            override fun onItemClick(view: View, user: User, pos: Int) {
                lastClickItem = pos
                SingleFAHelper.gotoTonguePicDetailFragment(activity!!, user)
            }
        }

        adapter.itemLongClickListener = object : TongNoteListAdapter.ItemLongClickListener {
            override fun onItemLongClick(view: View, user: User) {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("提示")
                builder.setMessage("确定删除该条记录？")
                builder.setCancelable(false)
                builder.setPositiveButton("确定") { dialog, which ->
                    ManagerFactory.getInstance(activity!!).getUserManager().delete(user)
                    dataList.remove(user)
                    adapter.notifyDataSetChanged()
                }
                builder.setNegativeButton("取消", null)
                builder.create().show()
            }
        }
    }

    override fun onChange(key: String, data: Any) {
        if (key.equals(ChangeListenerManager.CHANGELISTENERMANAGER_DB_INSERT)) {
            if (data is User) {
                dataList.add(0, data)
                adapter.notifyDataSetChanged()
            }
        }
        if (key.equals(ChangeListenerManager.CHANGELISTENERMANAGER_DB_READ) && lastClickItem != -1) {
            dataList[lastClickItem].isRead = true
            adapter.notifyItemChanged(lastClickItem)
        }
    }

    override fun onDestroy() {
        ChangeListenerManager.getInstance().unregisterListener(this)
        super.onDestroy()
    }
}
