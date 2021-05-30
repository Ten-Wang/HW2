package tw.teng.hw2.ui

import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tw.teng.hw2.resource.repository.model.*
import tw.teng.hw2.resource.utils.DataUtils


class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel(mockk())
        viewModel.setListItems(arrayListOf())
    }

    @Test
    fun `test setListItems`() {
        assertTrue(viewModel.listLiveData.value?.size == 0)
        val arrayList = DataUtils.getItemList()

        viewModel.setListItems(arrayList)
        assertTrue(viewModel.listLiveData.value?.size == arrayList.size)
    }
}