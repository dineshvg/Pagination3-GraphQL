package de.mobile.dinesh.gorillas.data.network.client

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.coroutines.toFlow
import de.mobile.dinesh.gorillas.GetPostByIdQuery
import de.mobile.dinesh.gorillas.PaginatePostsQuery
import de.mobile.dinesh.gorillas.type.PageQueryOptions
import de.mobile.dinesh.gorillas.type.PaginateOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class ApiClient(
    private val apollo: ApolloClient,
    private val maxItems: Int,
    private val dispatcher: CoroutineDispatcher
) {
    fun getPostListResponse(
        currentPage: Int
    ): Flow<PaginatePostsQuery.Data> =
        with(apollo) {
            query(
                PaginatePostsQuery(
                    options = PageQueryOptions(
                        paginate = PaginateOptions(
                            page = currentPage.toInput(),
                            limit = maxItems.toInput()
                        ).toInput()
                    ).toInput()
                )
            )
                .toFlow()
                .transform { response ->
                    response.data?.let { nonNullData: PaginatePostsQuery.Data ->
                        emit(nonNullData)
                    } ?: run {
                        Log.e(
                            "ApiClient",
                            "Error loading results from remote call ${response.errors}"
                        )
                    }
                }
                .flowOn(dispatcher)
        }

    fun getPostByIdResponse(
        postId: String
    ): Flow<GetPostByIdQuery.Data> =
        with(apollo) {
            query(
                GetPostByIdQuery(
                    id = postId
                )
            )
                .toFlow()
                .transform { response ->
                    response.data?.let { nonNullData: GetPostByIdQuery.Data ->
                        emit(nonNullData)
                    } ?: run {
                        Log.e(
                            "ApiClient",
                            "Error loading results from remote call ${response.errors}"
                        )
                    }
                }
                .flowOn(dispatcher)
        }
}