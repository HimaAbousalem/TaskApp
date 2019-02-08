package com.apps.abousalem.todoapptask.dagger.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.abousalem.todoapptask.dagger.qualifier.ActivityQualifier
import com.apps.abousalem.todoapptask.dagger.scope.ActivityScope
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import com.apps.abousalem.todoapptask.ui.login.LoginViewModel
import com.apps.abousalem.todoapptask.ui.task.TaskViewModel
import com.apps.abousalem.todoapptask.ui.taskdetails.TaskDetailsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity){

    @ActivityScope
    @Provides
    fun provideLoginViewModel(): LoginViewModel {
        return ViewModelProviders.of(activity).get(LoginViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun provideTaskViewModel(): TaskViewModel {
        return ViewModelProviders.of(activity).get(TaskViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun provideTaskDetailsViewModel(): TaskDetailsViewModel {
        return ViewModelProviders.of(activity).get(TaskDetailsViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun provideLinearLayoutManager():LinearLayoutManager{
        return LinearLayoutManager(activity)
    }

    @ActivityScope
    @Provides
    fun provideTaskRecyclerAdapter(): GroupAdapter<ViewHolder>{
        return GroupAdapter<ViewHolder>()
    }

}