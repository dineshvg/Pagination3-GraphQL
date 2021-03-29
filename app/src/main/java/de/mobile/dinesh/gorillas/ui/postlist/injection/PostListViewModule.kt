package de.mobile.dinesh.gorillas.ui.postlist.injection

import de.mobile.dinesh.gorillas.ui.postlist.viewmodel.PostListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postListViewModule = module {

    viewModel {
        PostListViewModel(get()).apply { initialize() }
    }

}