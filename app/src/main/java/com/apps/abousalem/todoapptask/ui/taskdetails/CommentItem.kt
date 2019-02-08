package com.apps.abousalem.todoapptask.ui.taskdetails

import com.apps.abousalem.todoapptask.R
import com.apps.abousalem.todoapptask.model.database.entities.TaskComments
import com.apps.abousalem.todoapptask.utils.DateUtility
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

import kotlinx.android.synthetic.main.comment_item.view.*

class CommentItem(private val comment: TaskComments): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.comment_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
       val item = viewHolder.itemView
        item.comment_txt_view.text = comment.commentText
        item.time_ago_txt_view.text = getRightTime()
    }

    private fun getRightTime(): String {
        val oldDate = comment.commentDate
        return DateUtility.calculateDate(oldDate)
    }

}