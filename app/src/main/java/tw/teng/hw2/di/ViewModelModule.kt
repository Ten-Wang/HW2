package tw.teng.hw2.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.teng.hw2.ui.MainViewModel

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        MainViewModel(get())
    }
}