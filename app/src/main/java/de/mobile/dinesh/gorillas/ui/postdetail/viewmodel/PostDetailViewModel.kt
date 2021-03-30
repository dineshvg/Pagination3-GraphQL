package de.mobile.dinesh.gorillas.ui.postdetail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mobile.dinesh.gorillas.domain.usecase.GetPostByIdUseCase
import de.mobile.dinesh.gorillas.ui.postdetail.viewdata.PostItemDetailViewData
import de.mobile.dinesh.gorillas.ui.postdetail.viewdata.toViewData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postId: String?,
    private val getPostByIdUseCase: GetPostByIdUseCase
) : ViewModel() {

    private val TAG by lazy { PostDetailViewModel::javaClass.name }

    sealed class State {
        data class Content(val post: PostItemDetailViewData) : State()
        object Loading : State()
        object NetworkError : State()
    }

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    fun load() {
        viewModelScope.launch {
            try {
                postId?.let {
                    getPostByIdUseCase(it)
                        .collectLatest { domainData ->
                            domainData.toViewData()?.let { post ->
                                _state.value = State.Content(post)
                            } ?: run {
                                Log.d(TAG, "data missing")
                                _state.value = State.NetworkError
                            }
                        }
                }

            } catch (e: Exception) {
                _state.value = State.NetworkError
            }
        }
    }
}