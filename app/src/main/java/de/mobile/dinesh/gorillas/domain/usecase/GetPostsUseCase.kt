package de.mobile.dinesh.gorillas.domain.usecase

import androidx.paging.PagingData
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryListPost
import de.mobile.dinesh.gorillas.domain.reposiotory.PostsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetPostsUseCase(
    private val repository: PostsRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Flow<PagingData<ZeroQueryListPost>> =
        repository.getPosts()
            .flowOn(dispatcher)
}