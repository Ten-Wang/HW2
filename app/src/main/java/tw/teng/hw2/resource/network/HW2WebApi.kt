package tw.teng.hw2.resource.network

import android.content.Context
import tw.teng.hw2.resource.network.api_interface.ContactInterface
import tw.teng.hw2.resource.network.model.APIResponse

class HW2WebApi constructor(context: Context) : WebApi(context) {

    companion object {
        private var instance: HW2WebApi? = null

        @Synchronized
        fun getInstance(context: Context): HW2WebApi {
            if (instance == null) {
                instance = HW2WebApi(context)
            }
            return instance!!
        }
    }

    fun status(listener: OnApiListener<APIResponse>) {
        apiRetrofit.create(ContactInterface::class.java)
            .getStatus()
            .enqueue(ApiCallback(listener))
    }

    fun wifiStatus(listener: OnApiListener<APIResponse>) {
        apiWifiRetrofit.create(ContactInterface::class.java)
            .getStatus()
            .enqueue(ApiCallback(listener))
    }
}
