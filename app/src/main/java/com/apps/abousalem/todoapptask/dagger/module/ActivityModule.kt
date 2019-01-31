package com.apps.abousalem.todoapptask.dagger.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.apps.abousalem.todoapptask.dagger.qualifier.ActivityQualifier
import com.apps.abousalem.todoapptask.dagger.scope.ActivityScope
import com.apps.abousalem.todoapptask.ui.base.BaseViewModel
import com.apps.abousalem.todoapptask.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity){

    @ActivityScope
    @Provides
    fun provideLoginViewModel(): LoginViewModel {
        return ViewModelProviders.of(activity).get(LoginViewModel::class.java)
    }

}