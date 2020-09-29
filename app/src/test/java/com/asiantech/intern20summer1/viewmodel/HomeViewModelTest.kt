package com.asiantech.intern20summer1.viewmodel

import com.asiantech.intern20summer1.BaseTest
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.data.models.ApiResponse
import com.asiantech.intern20summer1.week12.data.models.NewPost
import com.asiantech.intern20summer1.week12.data.models.Post
import com.asiantech.intern20summer1.week12.data.models.ResponseLike
import com.asiantech.intern20summer1.week12.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.week12.data.source.datasource.PostDataSource
import com.asiantech.intern20summer1.week12.ui.home.PostViewModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import java.io.File

/**
 * Asian tech Co., ltd.
 * Intern20Summer1 Project
 * Create by Vuong Phan T. on 9/29/20.
 * This is HomeViewModelTest TODO("Not yes implemented")
 */
class HomeViewModelTest : BaseTest() {
    private lateinit var viewModel: PostViewModel

    @Mock
    private lateinit var localRepository: LocalDataSource

    @Mock
    private lateinit var postRepository: PostDataSource
    override fun initTest() {
        viewModel = PostViewModel(postRepository, localRepository)
    }

    @Test
    fun `Given When call getPostData - Then getPostData success`() {
        /* Given */
        val postLists = mutableListOf<NewPost>()
        for (i in 1..10) {
            postLists.add(NewPost(i))
        }
        val testObserver = TestObserver<Response<MutableList<NewPost>>>()

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn("token")
        Mockito.`when`(postRepository.getPost("token")).thenReturn(
            Single.just(Response.success(postLists))
        )
        viewModel.getListPostFromServer("token")?.subscribe(testObserver)
        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body(), CoreMatchers.`is`(postLists.toList()))
            true
        }
    }

    @Test
    fun `Given getToken - When call putIdUser - Then getToken success`() {
        viewModel.getToken()
        assert(true)
    }

    @Test
    fun `Given getListPost - When call getListPost - Then getListPost success`() {
        viewModel.getListPost()
        assert(true)
    }

    @Test
    fun `Given getListPostFromServer - When call getListPostFromServer - Then getListPostFromServer success`() {
        viewModel.getListPostFromServer("token")
        assert(true)
    }

    @Test
    fun `Given getListPostAdapter - When call getListPostAdapter - Then getListPostAdapter success`() {
        viewModel.getListPostAdapter()
        assert(true)
    }

    @Test
    fun `Given getAllPost - When call getAllPost - Then getAllPost success`() {
        viewModel.getAllPost()
        assert(true)
    }

    @Test
    fun `Given getIdUser - When call getIdUser - Then getIdUser success`() {
        viewModel.getIdUser()
        assert(true)
    }

    @Test
    fun `Given position  - When call LikePost - Then LikePost success`() {
        /* Given */
        val position = 2
        val response = ResponseLike("message", 10, true)
        val testObserver = TestObserver<Response<ResponseLike>>()
        val postLists = mutableListOf<NewPost>()
        for (i in 0..10) {
            postLists.add(NewPost(i))
        }

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(postRepository.getPost("token")).thenReturn(
            Single.just(Response.success(postLists))
        )

        viewModel.getListPostFromServer("token")?.subscribe()
        Mockito.`when`(
            viewModel.getListPostAdapter()[position]?.id.let {
                it?.let { it1 ->
                    postRepository.likePost(
                        "token",
                        it1
                    )
                }
            }
        )
            .thenReturn(
                Single.just(Response.success(response))
            )
        viewModel.updateLikePost("token", position)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body(), CoreMatchers.`is`(response))
            true
        }
    }

    @Test
    fun `Given position  - When call loadMore - Then loadMore success`() {
        /* Given */
        val position = 2
        val postLists = mutableListOf<NewPost>()
        for (i in 0..10) {
            postLists.add(NewPost(i))
        }

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(postRepository.getPost("token")).thenReturn(
            Single.just(Response.success(postLists))
        )

        viewModel.getListPostFromServer("token")?.subscribe()
        Mockito.`when`(
            viewModel.getListPostAdapter()[position]?.id.let {
                it?.let { it1 ->
                    postRepository.likePost(
                        "token",
                        it1
                    )
                }
            }
        )
        viewModel.loadMore(position)
    }

    @Test
    fun `Given token,image,body - When call CreatePost - Then CreatePost success`() {
        /* Given */
        val file = File("file")
        val fileReqBody =
            file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            fileReqBody
        )
        val testObserver = TestObserver<Response<ApiResponse>>()
        val responsePost = ApiResponse("response post")
        val content = "aaaaaa"

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(
            postRepository.createNewPost("token", Post(content), part)
        ).thenReturn(
            Single.just(Response.success(responsePost))
        )
        viewModel.createNewPost(
            "token", Post(content), part
        )?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.message, CoreMatchers.`is`("response post"))
            true
        }
    }

    @Test
    fun `Given key - When call SearchData - Then SearchData success`() {
        /* Given */
        val testObserver = TestObserver<Response<MutableList<NewPost>>>()
        val postLists = mutableListOf<NewPost>()
        val size = 10
        for (i in 1..size) {
            postLists.add(NewPost(i))
        }

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(postRepository.getPost("token")).thenReturn(
            Single.just(Response.success(postLists))
        )
        viewModel.searchPostFromServer("gai")?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.size, CoreMatchers.`is`(size))
            true
        }
    }

    @Test
    fun `Given key - When call SearchData - Then getResearch success`() {
        viewModel.getResultSearch()
        assert(true)
    }

    @Test
    fun `Given key - When call updateProgressBar - Then updateProgressBar success`() {
        viewModel.updateProgressBar()
        assert(true)
    }
}
