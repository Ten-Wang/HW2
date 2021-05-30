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
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.teng.hw2.R
import tw.teng.hw2.databinding.ActivityMainBinding
import tw.teng.hw2.resource.utils.DataUtils


class MainActivity : AppCompatActivity() {

    val itemList = DataUtils.getItemList()
    private val viewModel by viewModel<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.listLiveData.observe(this, {
            (binding.recyclerView.adapter as RecyclerViewAdapter).setItems(it)
            (binding.recyclerView.adapter as RecyclerViewAdapter).notifyDataSetChanged()
        })
        viewModel.toastLiveData.observe(this, {
            showToast(it)
        })
        viewModel.activeTextListData.observe(this, {
            binding.tvActiveText.text = it
        })

        // init View
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
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
                    viewModel.onItemClick(position)
                }
            })
        recyclerView.adapter = adapter
        viewModel.setListItems(itemList)
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