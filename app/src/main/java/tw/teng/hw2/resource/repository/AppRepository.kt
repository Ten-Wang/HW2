package tw.teng.hw2.resource.repository

import android.app.Application
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import tw.teng.hw2.R
import tw.teng.hw2.resource.network.HW2WebApi
import tw.teng.hw2.resource.network.OnApiListener
import tw.teng.hw2.resource.network.model.APIResponse
import tw.teng.hw2.resource.repository.model.ListItem

class AppRepository private constructor(private val _application: Application, private val webApi: HW2WebApi) {

    companion object {
        private var instance: AppRepository? = null

        @Synchronized
        fun getInstance(application: Application,webApi: HW2WebApi): AppRepository {
            if (instance == null) {
                instance = AppRepository(application,webApi)
            }
            return instance!!
        }
    }

    var strToast = MutableLiveData<String>()
    var listItems = MutableLiveData<MutableList<ListItem>>()

    fun setListItems(list: MutableList<ListItem>) {
        listItems.postValue(list)
    }

    fun onCapabilitiesChanged(networkCapabilities: NetworkCapabilities) {
        when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                // wifi http
                webApi.wifiStatus(object :
                    OnApiListener<APIResponse> {
                    override fun onApiTaskSuccess(responseData: APIResponse) {
                        val string = _application.getString(
                            R.string.response_display,
                            responseData.status,
                            responseData.message
                        )
                        showToast(string)
                    }

                    override fun onApiTaskFailure(toString: String) {
                        val string = _application.getString(R.string.fail_message, toString)
                        showToast(string)
                    }
                })
            }
            networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) -> {
                // https api
                webApi.status(object :
                    OnApiListener<APIResponse> {
                    override fun onApiTaskSuccess(responseData: APIResponse) {
                        val string = _application.getString(
                            R.string.response_display,
                            responseData.status,
                            responseData.message
                        )
                        showToast(string)
                    }

                    override fun onApiTaskFailure(toString: String) {
                        val string = _application.getString(R.string.fail_message, toString)
                        strToast.postValue(string)
                    }
                })
            }
        }
    }

    private fun showToast(string: String) {
        strToast.postValue(string)
    }
}