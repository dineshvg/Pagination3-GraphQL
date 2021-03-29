package de.mobile.dinesh.gorillas.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import de.mobile.dinesh.gorillas.PaginatePostsQuery
import de.mobile.dinesh.gorillas.data.network.client.ApiClient
import de.mobile.dinesh.gorillas.data.network.mapper.toDomain
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryListPost
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException

class PostKeyedPagingSource(
    private val apiClient: ApiClient
) : PagingSource<Int, ZeroQueryListPost>() {
    override fun getRefreshKey(state: PagingState<Int, ZeroQueryListPost>): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ZeroQueryListPost> {
        val innerPosts = mutableListOf<ZeroQueryListPost>()
        var prevKey: Int? = null
        var nextKey: Int? = null

        try {
            apiClient.getPostListResponse(
                currentPage = params.key ?: 1
            ).collect { response ->
                response.posts?.let { dataFromPosts: PaginatePostsQuery.Posts ->
                    prevKey = dataFromPosts.links?.prev?.page
                    nextKey = dataFromPosts.links?.next?.page
                    dataFromPosts.data?.map { innerData ->
                        innerData?.toDomain()?.let { nonNullPost -> innerPosts.add(nonNullPost) }
                    }
                }
            }
            return LoadResult.Page(
                data = innerPosts.toList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}