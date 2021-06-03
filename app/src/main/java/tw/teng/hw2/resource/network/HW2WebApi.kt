package tw.teng.hw2.resource.network

import tw.teng.hw2.resource.network.api_interface.ContactInterface
import tw.teng.hw2.resource.network.model.APIResponse

class HW2WebApi constructor(apiUrlPrefix: String, apiWifiUrlPrefix: String) :
    WebApi(apiUrlPrefix, apiWifiUrlPrefix) {

    companion object {
        private var instance: HW2WebApi? = null

        @Synchronized
        fun getInstance(apiUrlPrefix: String, apiWifiUrlPrefix: String): HW2WebApi {
            if (instance == null) {
                instance = HW2WebApi(apiUrlPrefix, apiWifiUrlPrefix)
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
