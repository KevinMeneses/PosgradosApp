package co.edu.uniautonoma.posgradosapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class diApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@diApplication)
            androidFileProperties()
            modules(
                    listOf(
                            interceptorModule,
                            okHttpClientModule,
                            retrofitModule,
                            viewModelModule,
                            useCaseModule,
                            repositoryModule)
            )
        }
    }
}