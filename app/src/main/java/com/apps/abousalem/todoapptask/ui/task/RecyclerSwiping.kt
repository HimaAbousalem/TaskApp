package com.apps.abousalem.todoapptask.ui.task

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_row.view.*

class RecyclerSwiping(dragDir: Int, swipeDir: Int, private val listener: RecyclerItemTouchHelperListener) : ItemTouchHelper.SimpleCallback(dragDir,swipeDir) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView = viewHolder.itemView.foreground_view
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(viewHolder != null){
            val foregroundView = viewHolder.itemView.foreground_view
            getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView = viewHolder!!.itemView.foreground_view
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView = viewHolder.itemView.foreground_view
        getDefaultUIUtil().clearView(foregroundView)
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}