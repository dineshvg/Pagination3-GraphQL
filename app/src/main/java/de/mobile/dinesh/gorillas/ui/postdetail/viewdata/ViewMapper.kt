package de.mobile.dinesh.gorillas.ui.postdetail.viewdata

import de.mobile.dinesh.gorillas.data.network.mapper.safeLet
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryDetailPost

fun ZeroQueryDetailPost.toViewData() = Pair(user?.name, user?.username).safeLet { name, username ->
    PostItemDetailViewData(
        postId = id,
        title = title,
        description = detailedDesc,
        userName = username,
        name = name
    )
}