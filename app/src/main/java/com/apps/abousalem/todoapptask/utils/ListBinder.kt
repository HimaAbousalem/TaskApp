package com.apps.abousalem.todoapptask.utils

import androidx.recyclerview.widget.ListUpdateCallback
import com.apps.abousalem.todoapptask.ui.task.TaskRecyclerItem

class ListBinder(private val oldList: MutableList<TaskRecyclerItem>, private val newList: MutableList<TaskRecyclerItem>) : ListUpdateCallback{
    override fun onChanged(position: Int, count: Int, payload: Any?) {
            oldList.removeAt(position)
            oldList.add(position, newList[position])
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
    }

    //position  = where it gonna be start insert.
    //count = how many items will be inserted.
    override fun onInserted(position: Int, count: Int) {
        for(i in position until position+count){
            if(position == 0){
                oldList.add(i,newList[i])
            }else{
                val index = newList.indexOf(oldList[i-1])
                oldList.add(i,newList[index+1])
            }
        }
    }
    override fun onRemoved(position: Int, count: Int) {
        for (i in position until position+count) {
            oldList.removeAt(position)
        }
    }


}