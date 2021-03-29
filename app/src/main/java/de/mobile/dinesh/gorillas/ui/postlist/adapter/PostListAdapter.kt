package de.mobile.dinesh.gorillas.ui.postlist.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import de.mobile.dinesh.gorillas.ui.postlist.viewdata.PostItemViewData
import de.mobile.dinesh.gorillas.ui.postlist.viewholder.PostListItemViewHolder

class PostListAdapter : PagingDataAdapter<PostItemViewData, PostListItemViewHolder>(DiffUtilCallBack)  {

    lateinit var onPostSelected: (String) -> Unit

    object DiffUtilCallBack: DiffUtil.ItemCallback<PostItemViewData>() {
        override fun areItemsTheSame(oldItem: PostItemViewData, newItem: PostItemViewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItemViewData, newItem: PostItemViewData): Boolean {
            return oldItem.title == newItem.title && oldItem.shortDesc == newItem.shortDesc
        }
    }

    override fun onBindViewHolder(holder: PostListItemViewHolder, position: Int) {
        getItem(position)?.let { viewData ->
            holder.bind(viewData, onPostSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostListItemViewHolder.create(parent)
}