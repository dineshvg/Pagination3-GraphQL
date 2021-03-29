package de.mobile.dinesh.gorillas.data

import de.mobile.dinesh.gorillas.GetPostByIdQuery
import de.mobile.dinesh.gorillas.data.network.client.ApiClient
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf

internal class PostsRepositorySpec: WordSpec() {

    private val client: ApiClient = mockk()

    private lateinit var repository : PostsRepositoryImpl

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        repository = PostsRepositoryImpl(client)
    }

    init {
        "PostsRepository" should {

            "get a post and emit it" {

                coEvery { client.getPostByIdResponse(any()) } returns flowOf(
                    GetPostByIdQuery.Data(
                        post = GetPostByIdQuery.Post(
                            id = "id",
                            title = "title",
                            body = "body",
                            user = null
                        )
                    )
                )

               repository.getPost("1").collect { result ->
                   result shouldBe flowOf(
                       GetPostByIdQuery.Data(
                           post = GetPostByIdQuery.Post(
                               id = "id",
                               title = "title",
                               body = "body",
                               user = null
                           )
                       )
                   )
               }


                coVerify {
                    client.getPostByIdResponse("1")
                }
            }
        }
    }
}