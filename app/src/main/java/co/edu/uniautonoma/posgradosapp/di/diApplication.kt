package co.edu.uniautonoma.posgradosapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class diApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val moduleList = listOf(viewModelModule, useCaseModule, repositoryModule)
        startKoin{
            androidLogger()
            androidContext(this@diApplication)
            androidFileProperties()
            modules(moduleList)
        }
    }
}