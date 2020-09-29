package com.asiantech.intern20summer1.viewmodel

import android.net.Uri
import com.asiantech.intern20summer1.BaseTest
import com.asiantech.intern20summer1.w11.data.models.PostContent
import com.asiantech.intern20summer1.w11.data.models.PostItem
import com.asiantech.intern20summer1.w11.data.models.ResponseLike
import com.asiantech.intern20summer1.w11.data.models.ResponsePost
import com.asiantech.intern20summer1.w11.data.source.datasource.HomeDataSource
import com.asiantech.intern20summer1.w11.data.source.datasource.LocalDataSource
import com.asiantech.intern20summer1.w11.ui.fragment.home.HomeVM
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import java.io.File

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 25/09/2020.
 * This is LoginViewModelTest TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class HomeViewModelTest : BaseTest() {
    companion object {
        private const val TYPE_TEXT = "text"
    }

    private lateinit var viewModel: HomeVM

    @Mock
    private lateinit var localRepository: LocalDataSource

    @Mock
    private lateinit var homeRepository: HomeDataSource

    override fun initTest() {
        viewModel = HomeVM(homeRepository, localRepository)
    }

    @Test
    fun `Given When call getPostData - Then getPostData success`() {
        /* Given */
        val postLists = mutableListOf<PostItem>()
        for (i in 1..10) {
            postLists.add(PostItem(i))
        }
        val testObserver = TestObserver<Response<List<PostItem>>>()

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn("token")
        Mockito.`when`(homeRepository.getPosts("token")).thenReturn(
            Single.just(Response.success(postLists.toList()))
        )
        viewModel.getPostsData()?.subscribe(testObserver)
        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body(), CoreMatchers.`is`(postLists.toList()))
            true
        }
    }

    @Test
    fun `Given position  - When call LikePost - Then LikePost success`() {
        /* Given */
        val position = 2
        val response = ResponseLike("message", 10, true)
        val testObserver = TestObserver<Response<ResponseLike>>()
        val postLists = mutableListOf<PostItem>()
        for (i in 0..10) {
            postLists.add(PostItem(i))
        }

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(homeRepository.getPosts("token")).thenReturn(
            Single.just(Response.success(postLists.toList()))
        )

        viewModel.getPostsData()?.subscribe()
        Mockito.`when`(
            homeRepository.likePost(
                "token",
                viewModel.getDataAdapter()[position].id
            )
        )
            .thenReturn(
                Single.just(Response.success(response))
            )
        viewModel.likePost(position)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body(), CoreMatchers.`is`(response))
            true
        }
    }

    @Test
    fun `Given token,image,body - When call CreatePost - Then CreatePost success`() {
        /* Given */
        val postJson = Gson().toJson(PostContent("")).toString()
        val body = postJson.toRequestBody(TYPE_TEXT.toMediaTypeOrNull())
        val file = File("file")
        val testObserver = TestObserver<Response<ResponsePost>>()
        val responsePost = ResponsePost("response post")

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(localRepository.createMultiPartBody(any())).thenReturn(
            MultipartBody.Part.createFormData("image", file.name, body)
        )
        Mockito.`when`(homeRepository.createPost(anyString(), any(), any())).thenReturn(
            Single.just(Response.success(responsePost))
        )
        viewModel.createPost("", Uri.parse(""))?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.message, CoreMatchers.`is`("response post"))
            true
        }
    }

    @Test
    fun `Given isdPost, token,image,body - When call updatePost - Then updatePost success`() {

        /* Given */
        val postJson = Gson().toJson(PostContent("")).toString()
        val body = postJson.toRequestBody(TYPE_TEXT.toMediaTypeOrNull())
        val file = File("file")
        val testObserver = TestObserver<Response<ResponsePost>>()
        val responsePost = ResponsePost("response post")
        val part = MultipartBody.Part.createFormData("image", file.name, body)
        val uri = Uri.parse("Uri")
        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn("token")
        Mockito.`when`(localRepository.createMultiPartBody(uri)).thenReturn(part)
        Mockito.`when`(homeRepository.updatePost("token", 1, part, body)).thenReturn(
            Single.just(Response.success(responsePost))
        )
        viewModel.updatePost(1, "", uri)?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body(), CoreMatchers.`is`(responsePost))
            true
        }
    }

    @Test
    fun `Given key - When call SearchData - Then SearchData success`() {
        /* Given */
        val testObserver = TestObserver<Response<List<PostItem>>>()
        val postLists = mutableListOf<PostItem>()
        val size = 10
        for (i in 1..size) {
            postLists.add(PostItem(i))
        }

        /* When */
        Mockito.`when`(localRepository.getToken()).thenReturn(
            "token"
        )
        Mockito.`when`(homeRepository.getPosts("token")).thenReturn(
            Single.just(Response.success(postLists.toList()))
        )
        viewModel.searchData("gai")?.subscribe(testObserver)

        /* Then */
        testObserver.assertValue {
            Assert.assertThat(it.body()?.size, CoreMatchers.`is`(size))
            true
        }
    }

}
