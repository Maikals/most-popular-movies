
package com.example.miquelcastanys.cleanlearning.injector.module

import android.content.Context
import com.example.domain.executor.PostExecutionThread
import com.example.miquelcastanys.cleanlearning.MostPopularMoviesApplication
import com.example.miquelcastanys.cleanlearning.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MostPopularMoviesApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideUIThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }
}