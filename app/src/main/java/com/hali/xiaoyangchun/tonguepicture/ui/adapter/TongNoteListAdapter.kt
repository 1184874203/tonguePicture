package com.hali.xiaoyangchun.tonguepicture.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hali.xiaoyangchun.tonguepicture.R
import com.hali.xiaoyangchun.tonguepicture.bean.User
import java.text.SimpleDateFormat
import java.util.*

class TongNoteListAdapter(private var context: Context, private var mData: MutableList<User>? = null) : RecyclerView.Adapter<TongNoteListAdapter.NodeHolder>(), View.OnLongClickListener {
    var timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_node, parent, false)
        view.setOnLongClickListener(this)
        return NodeHolder(view)
    }

    override fun onBindViewHolder(holder: NodeHolder, position: Int) {
        val note = mData!![position]
        holder.itemView.setTag(note)
        holder.tv_list_title.setText(note.name)
        holder.tv_list_Summary.setText(note.otherString)
        holder.tv_list_time.setText(timeFormat.format(Date(note.time)))
        holder.iv_dot.visibility = if (note.isRead) View.GONE else View.VISIBLE
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(it, note, position)
        }
    }

    override fun getItemCount() = if (mData != null) mData!!.size else 0

    override fun onLongClick(v: View): Boolean {
        itemLongClickListener?.onItemLongClick(v, v.tag as User)
        return true
    }

    var itemClickListener: ItemClickListener? = null
    interface ItemClickListener {
        fun onItemClick(view: View, user: User, pos: Int)
    }

    var itemLongClickListener: ItemLongClickListener? = null
    interface ItemLongClickListener {
        fun onItemLongClick(view: View, user: User)
    }

    class NodeHolder : RecyclerView.ViewHolder {
        lateinit var tv_list_title: TextView
        lateinit var tv_list_Summary: TextView
        lateinit var tv_list_time: TextView
        lateinit var iv_dot: ImageView

        constructor(view: View) : super(view) {
            tv_list_title = view.findViewById(R.id.tv_list_title)
            tv_list_time = view.findViewById(R.id.tv_list_time)
            tv_list_Summary = view.findViewById(R.id.tv_list_summary)
            iv_dot = view.findViewById(R.id.iv_red_dot)
        }
    }
}