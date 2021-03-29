package de.mobile.dinesh.gorillas.domain.usecase

import de.mobile.dinesh.gorillas.domain.model.ZeroQueryDetailPost
import de.mobile.dinesh.gorillas.domain.reposiotory.PostsRepository
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
internal class GetPostByIdUseCaseSpec : WordSpec() {

    private val dispatcher = TestCoroutineDispatcher()

    private val repository: PostsRepository = mockk()

    private lateinit var useCase: GetPostByIdUseCase

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        useCase = GetPostByIdUseCase(
            repository, dispatcher
        )
    }

    init {

        "GetPostByIdUseCase" should {

            "get the post from the repository based on the post Id" {

                coEvery { repository.getPost(any()) } returns flowOf(
                    ZeroQueryDetailPost(
                        id = "id",
                        title = "title",
                        detailedDesc = "desc",
                        user = null
                    )
                )

                val flow = useCase("1")

                flow.collect { result: ZeroQueryDetailPost ->
                    result shouldBe ZeroQueryDetailPost(
                        id = "id",
                        title = "title",
                        detailedDesc = "desc",
                        user = null
                    )

                }

                coVerify { repository.getPost("1") }

            }
        }
    }

}