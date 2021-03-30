package de.mobile.dinesh.gorillas.ui.postlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import de.mobile.dinesh.gorillas.domain.usecase.GetPostsUseCase
import de.mobile.dinesh.gorillas.ui.postlist.viewdata.PostItemViewData
import de.mobile.dinesh.gorillas.ui.postlist.viewdata.toViewData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostListViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    sealed class State {
        data class Content(val posts: PagingData<PostItemViewData>) : State()
        object Loading : State()
        object NetworkError : State()
    }

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    fun initialize() {
        viewModelScope.launch {
            _state.value = State.Loading
            getPostsUseCase()
                .cachedIn(viewModelScope)
                .runCatching {
                    collect { pagingData ->
                        _state.value = State.Content(pagingData.map { it.toViewData() })
                    }
                }
        }
    }

    fun setStateToError() {
        _state.value = State.NetworkError
    }
}