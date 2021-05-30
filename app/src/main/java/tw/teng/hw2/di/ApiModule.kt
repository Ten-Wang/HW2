package tw.teng.hw2.di

import android.content.Context
import org.koin.dsl.module
import tw.teng.hw2.resource.network.HW2WebApi

val apiModule = module {
    fun provideHW2WebApi(context: Context): HW2WebApi {
        return HW2WebApi.getInstance(context)
    }
    single {
        provideHW2WebApi(get())
    }
}