package de.mobile.dinesh.gorillas.ui.postlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.mobile.dinesh.gorillas.databinding.ItemPostRowBinding
import de.mobile.dinesh.gorillas.ui.postlist.viewdata.PostItemViewData

class PostListItemViewHolder(private val binding: ItemPostRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup) = PostListItemViewHolder(
            ItemPostRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun bind(item: PostItemViewData, onPostSelected: (String) -> Unit) {
        with(binding) {
            title.text = item.title
            shortDescription.text = item.shortDesc
            identification.text = item.id
            root.setOnClickListener {
                onPostSelected(item.id)
            }
        }
    }
}