package tw.teng.hw2

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import tw.teng.hw2.di.apiModule
import tw.teng.hw2.di.repositoryModule
import tw.teng.hw2.di.viewModelModule


class HW2Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            // inject Android context
            androidContext(this@HW2Application)
            // use modules
            modules(
                apiModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}