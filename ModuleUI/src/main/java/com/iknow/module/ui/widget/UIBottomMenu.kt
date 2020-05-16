package com.iknow.module.ui.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.iknow.lib.tools.ResourceUtil
import com.iknow.module.ui.R
import java.util.*

/**
 * 这是一个通用的底部菜单
 */
open class UIBottomMenu
private constructor(context: Context, list: List<String>) :
    Dialog(context, R.style.UiBottomMenuDialog) {

    companion object {
        fun with(context: Context): Build {
            return Build(context)
        }
    }

    private var listener: OnItemClickListener? = null

    init {
        setContentView(R.layout.ui_bottom_dialog)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCanceledOnTouchOutside(true)
        window!!.setGravity(Gravity.BOTTOM)

        findViewById<View>(R.id.cancel).setOnClickListener {
            dismiss()
        }

        var rv = findViewById<RecyclerView>(R.id.rv)
        var layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = layoutManager
        val adapter = object :
            BaseQuickAdapter<String, BaseViewHolder>(R.layout.ui_bottom_dialog_menu_item, list) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(R.id.tv_content, item)
            }
        }
        adapter.setOnItemClickListener { _, view, position ->
            dismiss()
            listener?.onClick(view, position)
        }
        rv.adapter = adapter
        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = ResourceUtil.getDimen(R.dimen.resource_4)
            }
        })


    }

    private fun setItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    open class Build {

        private val context: Context

        private var list: List<String>? = null;

        private var listener: OnItemClickListener? = null

        constructor(context: Context) {
            Objects.nonNull(context)
            this.context = context
        }

        fun menuList(list: List<String>): Build {
            Objects.nonNull(list)
            this.list = list
            return this
        }

        fun menuList(list: Array<String>): Build {
            Objects.nonNull(list)
            this.list = list.toList()
            return this
        }

        fun itemClickListener(listener: OnItemClickListener): Build {
            Objects.nonNull(listener)
            this.listener = listener
            return this
        }

        fun build(): UIBottomMenu {
            Objects.nonNull(list)
            val result = UIBottomMenu(context, list!!)
            listener?.let { result.setItemListener(it) }
            return result
        }

    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

}