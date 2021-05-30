package tw.teng.hw2.ui

import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.teng.hw2.resource.repository.AppRepository
import tw.teng.hw2.resource.repository.model.ListItem

open class MainViewModel(private val appRepo: AppRepository) :
    ViewModel() {

    var listLiveData: MutableLiveData<MutableList<ListItem>> = appRepo.listItems
    var toastLiveData: MutableLiveData<String> = appRepo.strToast

    fun setListItems(list: MutableList<ListItem>) {
        appRepo.setListItems(list)
    }

    fun onCapabilitiesChanged(networkCapabilities: NetworkCapabilities) {
        appRepo.onCapabilitiesChanged(networkCapabilities)
    }
}