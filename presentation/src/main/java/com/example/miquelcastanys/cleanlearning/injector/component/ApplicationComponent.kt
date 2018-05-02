
package com.example.miquelcastanys.cleanlearning.injector.component

import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(baseActivity: BaseActivity)

}