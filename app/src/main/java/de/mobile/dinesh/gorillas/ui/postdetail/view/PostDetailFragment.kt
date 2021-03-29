package de.mobile.dinesh.gorillas.ui.postdetail.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.mobile.dinesh.gorillas.R
import de.mobile.dinesh.gorillas.databinding.FragmentPostDetailBinding
import de.mobile.dinesh.gorillas.ui.extensions.observe
import de.mobile.dinesh.gorillas.ui.postdetail.viewdata.PostItemDetailViewData
import de.mobile.dinesh.gorillas.ui.postdetail.viewmodel.PostDetailViewModel
import de.mobile.dinesh.gorillas.ui.postdetail.viewmodel.PostDetailViewModel.State.Content
import de.mobile.dinesh.gorillas.ui.postdetail.viewmodel.PostDetailViewModel.State.Loading
import org.koin.android.ext.android.get
import org.koin.core.KoinComponent

class PostDetailFragment : Fragment(R.layout.fragment_post_detail), KoinComponent {

    private lateinit var binding: FragmentPostDetailBinding
    private val postId: String? by lazy { getIdOfPost(arguments) }
    private val viewModel by lazy { PostDetailViewModel(postId, get()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailBinding.bind(view)
        viewModel.load()
        observe(viewModel.state, ::onStateChange)
    }

    private fun onStateChange(state: PostDetailViewModel.State) {
        when (state) {
            is Content -> {
                binding.content.isVisible = true
                binding.loadingView.root.isVisible = false
                binding.errorView.isVisible = false
                bindContent(state.post)

            }
            Loading -> {
                binding.content.isVisible = false
                binding.loadingView.root.isVisible = true
                binding.errorView.isVisible = false
            }
            else -> {
                binding.content.isVisible = false
                binding.loadingView.root.isVisible = false
                binding.errorView.isVisible = true
                Log.e(PostDetailFragment::javaClass.name, state.toString())
            }
        }

    }

    private fun bindContent(post: PostItemDetailViewData) {
        binding.detailTitle.text = post.title
        binding.detailDesc.text = post.description
        binding.username.text = post.userName
        binding.name.text = post.name
    }

    private fun getIdOfPost(args: Bundle?): String? =
        args?.getString("selected_id")

}