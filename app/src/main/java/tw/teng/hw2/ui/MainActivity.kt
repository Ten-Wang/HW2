package tw.teng.hw2.ui

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.teng.hw2.R
import tw.teng.hw2.resource.network.HW2WebApi
import tw.teng.hw2.resource.network.OnApiListener
import tw.teng.hw2.resource.network.model.APIResponse
import tw.teng.hw2.resource.repository.model.*
import tw.teng.hw2.resource.utils.TimeUtils


class MainActivity : AppCompatActivity() {

    private lateinit var tvNetworkStatus: TextView
    private lateinit var tvActiveText: TextView
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvNetworkStatus = findViewById(R.id.tv_network_status)
        tvActiveText = findViewById(R.id.tv_active_text)

        recyclerView = findViewById(R.id.recycler_view)
        val itemDecorator =
            DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecorator)

        val arrayList = arrayListOf(
            TitleItem("DAY PASS"),
            DayPass(),
            Day3Pass(),
            Day7Pass(),
            TitleItem("HOUR PASS"),
            HourPass(),
            Hour8Pass()
        )

        val adapter = RecyclerViewAdapter(arrayList,
            object : RecyclerViewAdapter.ListItemAdapterListener {
                override fun onItemClick(position: Int) {
                    val str1 = (arrayList[position] as HourPass).name
                    val strCurrentTime = TimeUtils.toString(System.currentTimeMillis())
                    val expiredTime = TimeUtils.toString(
                        System.currentTimeMillis() + (arrayList[position] as HourPass).duration
                    )
                    tvActiveText.text =
                        getString(R.string.tv_active_text, str1, strCurrentTime, expiredTime)
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
                        tvNetworkStatus.text =
                            (getString(R.string.network_capabilities) + getString(R.string.transport_wifi))
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
                }
                networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) -> {
                    runOnUiThread {
                        tvNetworkStatus.text =
                            (getString(R.string.network_capabilities) + getString(R.string.transport_cellular))

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
}