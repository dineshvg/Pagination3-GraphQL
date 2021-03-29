package de.mobile.dinesh.gorillas.domain.reposiotory

import androidx.paging.PagingData
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryDetailPost
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryListPost
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun getPosts(): Flow<PagingData<ZeroQueryListPost>>

    suspend fun getPost(id: String): Flow<ZeroQueryDetailPost>
}