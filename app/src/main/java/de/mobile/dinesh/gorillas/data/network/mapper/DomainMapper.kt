package de.mobile.dinesh.gorillas.data.network.mapper

import de.mobile.dinesh.gorillas.GetPostByIdQuery
import de.mobile.dinesh.gorillas.PaginatePostsQuery
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryDetailPost
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryListPost
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryPostUser
import io.multifunctions.models.Quad
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

private const val MAX_SHORT_DESC_LENGTH = 120

fun PaginatePostsQuery.Data1.toDomain() =
    Quad(id, body, title, user).safeLet { id, body, title, _ ->
        ZeroQueryListPost(
            id = id,
            title = title,
            shortDesc = body.take(MAX_SHORT_DESC_LENGTH),
        )
    }

fun GetPostByIdQuery.Post.toDomain() =
    Quad(id, body, title, user).safeLet { id, body, title, user ->
        ZeroQueryDetailPost(
            id = id,
            detailedDesc = body,
            title = title,
            user = user.toDomain()
        )
    }

fun GetPostByIdQuery.User.toDomain() =
    Pair(name, username).safeLet { name, username -> userConverter(name, username) }

private fun userConverter(name: String, username: String) = ZeroQueryPostUser(
    username = username,
    name = name
)

@OptIn(ExperimentalContracts::class)
fun <A : Any, B : Any, R> Pair<A?, B?>.safeLet(body: (A, B) -> R): R? {
    contract {
        callsInPlace(body, InvocationKind.AT_MOST_ONCE)
    }
    return if (first == null || second == null) {
        null
    } else {
        body(first!!, second!!)
    }
}

@OptIn(ExperimentalContracts::class)
fun <A : Any, B : Any, C : Any, D : Any, R> Quad<A?, B?, C?, D?>.safeLet(body: (A, B, C, D) -> R): R? {
    contract {
        callsInPlace(body, InvocationKind.AT_MOST_ONCE)
    }
    return if (first == null || second == null || third == null || fourth == null) {
        null
    } else {
        body(first!!, second!!, third!!, fourth!!)
    }
}