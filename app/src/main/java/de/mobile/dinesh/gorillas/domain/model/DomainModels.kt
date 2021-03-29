package de.mobile.dinesh.gorillas.domain.model

data class ZeroQueryListPost(
    val id: String,
    val title: String,
    val shortDesc: String,
)

data class ZeroQueryDetailPost(
    val id: String,
    val title: String,
    val detailedDesc: String,
    val user: ZeroQueryPostUser?
)

data class ZeroQueryPostUser(
    val username: String,
    val name: String
)