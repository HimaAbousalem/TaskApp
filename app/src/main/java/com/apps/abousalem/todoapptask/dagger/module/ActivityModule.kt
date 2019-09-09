package com.apps.abousalem.todoapptask.dagger.module

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.abousalem.todoapptask.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: FragmentActivity){

    @ActivityScope
    @Provides
    fun provideContext():FragmentActivity = activity

    @ActivityScope
    @Provides
    fun provideLinearLayoutManager():LinearLayoutManager= LinearLayoutManager(activity)
}