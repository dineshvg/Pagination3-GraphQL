package de.mobile.dinesh.gorillas.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import de.mobile.dinesh.gorillas.data.network.client.ApiClient
import de.mobile.dinesh.gorillas.data.network.mapper.toDomain
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryDetailPost
import de.mobile.dinesh.gorillas.domain.reposiotory.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class PostsRepositoryImpl(
    private val apiClient: ApiClient
) : PostsRepository {

    override suspend fun getPosts() = Pager(
        config = PagingConfig(
            pageSize = 25,
            enablePlaceholders = false
        )
    ) { PostKeyedPagingSource(apiClient = apiClient) }.flow

    override suspend fun getPost(id: String): Flow<ZeroQueryDetailPost> =
        apiClient.getPostByIdResponse(id).transform { remoteData ->
            remoteData.post?.toDomain()?.let { nonNullData ->
                emit(nonNullData)
            }
        }
}