package de.mobile.dinesh.gorillas.domain.usecase

import de.mobile.dinesh.gorillas.domain.model.ZeroQueryDetailPost
import de.mobile.dinesh.gorillas.domain.reposiotory.PostsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetPostByIdUseCase(
    private val repository: PostsRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(postId: String): Flow<ZeroQueryDetailPost> =
        repository.getPost(postId)
            .flowOn(dispatcher)
}