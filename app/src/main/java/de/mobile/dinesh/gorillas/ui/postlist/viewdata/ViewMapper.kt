package de.mobile.dinesh.gorillas.ui.postlist.viewdata

import de.mobile.dinesh.gorillas.domain.model.ZeroQueryListPost

fun ZeroQueryListPost.toViewData() = PostItemViewData(
    id = id,
    title = title,
    shortDesc = shortDesc
)