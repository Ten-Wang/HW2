package tw.teng.hw2.resource.repository

import android.app.Application
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import tw.teng.hw2.R
import tw.teng.hw2.resource.network.HW2WebApi
import tw.teng.hw2.resource.network.OnApiListener
import tw.teng.hw2.resource.network.model.APIResponse
import tw.teng.hw2.resource.repository.model.ListItem

class AppRepository private constructor(private val _application: Application) {

    companion object {
        private var instance: AppRepository? = null

        @Synchronized
        fun getInstance(application: Application): AppRepository {
            if (instance == null) {
                instance = AppRepository(application)
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
                HW2WebApi.getInstance(_application).wifiStatus(object :
                    OnApiListener<APIResponse> {
                    override fun onApiTaskSuccess(responseData: APIResponse) {
                        val string = _application.getString(
                            R.string.response_display,
                            responseData.status,
                            responseData.message
                        )
                        strToast.postValue(string)
                    }

                    override fun onApiTaskFailure(toString: String) {
                        val string = _application.getString(R.string.fail_message, toString)
                        strToast.postValue(string)
                    }
                })
            }
            networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) -> {
                // https api
                HW2WebApi.getInstance(_application).status(object :
                    OnApiListener<APIResponse> {
                    override fun onApiTaskSuccess(responseData: APIResponse) {
                        val string = _application.getString(
                            R.string.response_display,
                            responseData.status,
                            responseData.message
                        )
                        strToast.postValue(string)
                    }

                    override fun onApiTaskFailure(toString: String) {
                        val string = _application.getString(R.string.fail_message, toString)
                        strToast.postValue(string)
                    }
                })
            }
        }
    }
}