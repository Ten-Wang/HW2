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

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        val adapter = RecyclerViewAdapter(arrayList,
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
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
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
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    runOnUiThread {
                        binding.tvNetworkStatus.text =
                            (getString(R.string.network_capabilities) + getString(R.string.transport_wifi))
                    }
                    // wifi http
                    HW2WebApi.getInstance(applicationContext).wifiStatus(object :
                        OnApiListener<APIResponse> {
                        override fun onApiTaskSuccess(responseData: APIResponse) {
                            Toast.makeText(
                                applicationContext,
                                getString(
                                    R.string.response_display,
                                    responseData.status,
                                    responseData.message
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onApiTaskFailure(toString: String) {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.fail_message, toString),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }
                networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) -> {
                    runOnUiThread {
                        binding.tvNetworkStatus.text =
                            (getString(R.string.network_capabilities) + getString(R.string.transport_cellular))
                    }
                    // https api
                    HW2WebApi.getInstance(applicationContext).status(object :
                        OnApiListener<APIResponse> {
                        override fun onApiTaskSuccess(responseData: APIResponse) {
                            Toast.makeText(
                                applicationContext,
                                getString(
                                    R.string.response_display,
                                    responseData.status,
                                    responseData.message
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onApiTaskFailure(toString: String) {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.fail_message, toString),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }
}