package tw.teng.hw2.ui

import android.app.Application
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import tw.teng.hw2.resource.repository.AppRepository
import tw.teng.hw2.resource.repository.model.ListItem

open class MainViewModel(application: Application) : AndroidViewModel(application) {
    var appRepo: AppRepository = AppRepository.getInstance(application)

    var listLiveData: MutableLiveData<MutableList<ListItem>> = appRepo.listItems
    var toastLiveData: MutableLiveData<String> = appRepo.strToast

    fun setListItems(list: MutableList<ListItem>) {
        appRepo.setListItems(list)
    }

    fun onCapabilitiesChanged(networkCapabilities: NetworkCapabilities) {
        appRepo.onCapabilitiesChanged(networkCapabilities)
    }
}