package tw.teng.hw2.ui

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import tw.teng.hw2.R
import tw.teng.hw2.databinding.ActivityMainBinding
import tw.teng.hw2.resource.network.HW2WebApi
import tw.teng.hw2.resource.network.OnApiListener
import tw.teng.hw2.resource.network.model.APIResponse
import tw.teng.hw2.resource.repository.model.*
import tw.teng.hw2.resource.utils.TimeUtils


class MainActivity : AppCompatActivity() {

    val arrayList = arrayListOf(
        TitleItem("DAY PASS"),
        DayPass(),
        Day3Pass(),
        Day7Pass(),
        TitleItem("HOUR PASS"),
        HourPass(),
        Hour8Pass()
    )
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.listLiveData.observe(this, {
            (binding.recyclerView.adapter as RecyclerViewAdapter).setItems(it)
        })
        viewModel.toastLiveData.observe(this, {
            showToast(it)
        })
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val recyclerView = binding.recyclerView
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )
        val adapter = RecyclerViewAdapter(
            arrayListOf(),
            object : RecyclerViewAdapter.ListItemAdapterListener {
                override fun onItemClick(position: Int) {
                    val strName = (arrayList[position] as HourPass).name
                    val strCurrentTime = TimeUtils.toString(System.currentTimeMillis())
                    val strExpiredTime = TimeUtils.toString(
                        System.currentTimeMillis() + (arrayList[position] as HourPass).duration
                    )
                    binding.tvActiveText.text =
                        getString(R.string.tv_active_text, strName, strCurrentTime, strExpiredTime)
                }
            })
        recyclerView.adapter = adapter

        viewModel.setListItems(arrayList)
    }

    override fun onResume() {
        super.onResume()
        registerNetworkStatus()
    }

    private fun registerNetworkStatus() {
        val networkService =
            this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkService.registerDefaultNetworkCallback(networkCallback)
        } else {
            networkService.registerNetworkCallback(
                NetworkRequest.Builder().build(),
                networkCallback
            )
        }
    }

    override fun onPause() {
        super.onPause()
        unRegisterNetworkStatus()
    }

    private fun unRegisterNetworkStatus() {
        val networkService =
            this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        networkService.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback: NetworkCallback = object : NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            viewModel.onCapabilitiesChanged(networkCapabilities)
            runOnUiThread {
                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        binding.tvNetworkStatus.text =
                            (getString(R.string.network_capabilities) + getString(R.string.transport_wifi))

                    }
                    networkCapabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ) -> {
                        binding.tvNetworkStatus.text =
                            (getString(R.string.network_capabilities) + getString(R.string.transport_cellular))
                    }
                }
            }
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(
            applicationContext,
            string,
            Toast.LENGTH_SHORT
        ).show()
    }
}