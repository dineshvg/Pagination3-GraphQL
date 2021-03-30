package de.mobile.dinesh.gorillas.ui.postlist.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.mobile.dinesh.gorillas.R
import de.mobile.dinesh.gorillas.databinding.FragmentPostListBinding
import de.mobile.dinesh.gorillas.ui.extensions.observe
import de.mobile.dinesh.gorillas.ui.postlist.adapter.PostListAdapter
import de.mobile.dinesh.gorillas.ui.postlist.viewmodel.PostListViewModel
import de.mobile.dinesh.gorillas.ui.postlist.viewmodel.PostListViewModel.State
import de.mobile.dinesh.gorillas.ui.postlist.viewmodel.PostListViewModel.State.Content
import de.mobile.dinesh.gorillas.ui.postlist.viewmodel.PostListViewModel.State.Loading
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PostListFragment : Fragment(R.layout.fragment_post_list) {

    private val viewModel: PostListViewModel by inject()
    private val postListAdapter by lazy { PostListAdapter() }
    private lateinit var binding: FragmentPostListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostListBinding.bind(view)

        binding.postlist.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = postListAdapter
            postListAdapter.onPostSelected= { navigateToDetail(it) }
        }

        lifecycleScope.launch {
            postListAdapter.loadStateFlow.collectLatest { pagingStates ->
                when(pagingStates.refresh ) {
                    is LoadState.Error -> viewModel.setStateToError()
                    else -> {
                        //allow the natural flow to show the data
                    }
                }
            }
        }

        observe(viewModel.state, ::onStateChanged)
    }

    private fun navigateToDetail(id: String) {
        findNavController().navigate(
            R.id.postDetail,
            Bundle().apply { putString("selected_id", id) }
        )
    }

    private fun onStateChanged(state: State) {
        when(state) {
            is Content -> {
                binding.postlist.isVisible = true
                postListAdapter.submitData(lifecycle, state.posts)
                binding.loadingView.root.isVisible = false
                binding.errorView.isVisible = false
            }
            Loading -> {
                binding.loadingView.root.isVisible = true
                binding.errorView.isVisible = false
            }
            else -> {
                binding.loadingView.root.isVisible = false
                binding.errorView.isVisible = true
                Log.e("Error", "something went wrong while fetching the lists")
            }
        }
    }

}