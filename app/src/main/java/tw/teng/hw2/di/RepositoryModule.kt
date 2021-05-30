package tw.teng.hw2.di

import android.app.Application
import org.koin.dsl.module
import tw.teng.hw2.resource.network.HW2WebApi
import tw.teng.hw2.resource.repository.AppRepository

val repositoryModule = module {
    fun providerAppRepo(context: Application, api: HW2WebApi): AppRepository {
        return AppRepository.getInstance(application = context, api)
    }
    single {
        providerAppRepo(get(), get())
    }
}