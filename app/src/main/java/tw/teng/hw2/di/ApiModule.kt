package tw.teng.hw2.di

import android.content.Context
import org.koin.dsl.module
import tw.teng.hw2.R
import tw.teng.hw2.resource.network.HW2WebApi

val apiModule = module {
    fun provideHW2WebApi(context: Context): HW2WebApi {
        val apiUrlPrefix: String = context.getString(R.string.https_api_url_prefix)
        val apiWifiUrlPrefix: String = context.getString(R.string.wifi_api_url_prefix)
        return HW2WebApi.getInstance(apiUrlPrefix, apiWifiUrlPrefix)
    }
    single {
        provideHW2WebApi(get())
    }
}