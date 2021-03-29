package de.mobile.dinesh.gorillas.domain.usecase

import androidx.paging.PagingData
import de.mobile.dinesh.gorillas.domain.model.ZeroQueryListPost
import de.mobile.dinesh.gorillas.domain.reposiotory.PostsRepository
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
internal class GetPostsUseCaseSpec : WordSpec() {

    private val dispatcher = TestCoroutineDispatcher()

    private val repository: PostsRepository = mockk()


    private lateinit var useCase: GetPostsUseCase

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        useCase = GetPostsUseCase(
            repository, dispatcher
        )
    }

    init {

        "getPostsUseCase" should {

            "get the list of posts from the paging source" {

                coEvery { repository.getPosts() } returns flowOf(PAGING_DATA)

                val flow = useCase()

                flow.collect { result: PagingData<ZeroQueryListPost> ->
                    result shouldBe PAGING_DATA
                }
            }
        }
    }

    companion object {

        private val PAGING_DATA = PagingData.from(
            listOf(
                ZeroQueryListPost(
                    id = "id",
                    title = "title",
                    shortDesc = "desc"
                )
            )
        )
    }
}