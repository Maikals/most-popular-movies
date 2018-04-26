
package com.example.miquelcastanys.cleanlearning.injector.module

import android.content.Context
import com.example.miquelcastanys.cleanlearning.CleanLearningApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: CleanLearningApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Context {
        return application
    }
}