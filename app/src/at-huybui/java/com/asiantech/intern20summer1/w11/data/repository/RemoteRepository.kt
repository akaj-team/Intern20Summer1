package com.asiantech.intern20summer1.w11.data.repository

import android.content.Context
import android.net.Uri
import com.asiantech.intern20summer1.w11.data.models.*
import com.asiantech.intern20summer1.w11.data.remotedatasource.AccountDataSource
import com.asiantech.intern20summer1.w11.data.remotedatasource.AccountRemoteDataSource
import com.asiantech.intern20summer1.w11.data.remotedatasource.PostsRemoteDataSource
import com.asiantech.intern20summer1.w11.utils.FileInformation
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 17/09/2020.
 * This is PostsRepository TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class RemoteRepository(private val context: Context) : AccountDataSource {
    companion object {
        private const val TYPE_IMAGE = "image"
    }

    private val postRemote = PostsRemoteDataSource()
    private val accountRemote = AccountRemoteDataSource()
    fun getPosts(token: String): Observable<Response<List<PostItem>>>? =
        postRemote.getPosts(token)

    fun createPost(
        token: String,
        image: MultipartBody.Part?,
        body: RequestBody
    ): Observable<Response<ResponsePost>>? =
        postRemote.createPost(token, image, body)


    fun likePost(token: String, id: Int): Observable<Response<ResponseLike>>? =
        postRemote.likePost(token, id)

    override fun autoSignIn(token: String): Observable<Response<Account>>? =
        accountRemote.autoSignIn(token)

    override fun login(email: String, password: String): Observable<Response<Account>>? =
        accountRemote.login(email, password)

    override fun createUser(request: RequestAccount): Observable<Response<Account>>? =
        accountRemote.createUser(request)


    fun updatePost(
        token: String,
        id: Int,
        image: String?,
        body: RequestBody
    ): Single<Response<ResponsePost>>? {
        val file = createMultiPartBody(image)
        return postRemote.updatePost(token, id, file, body)
    }

    private fun createMultiPartBody(uri: String?): MultipartBody.Part? {
        uri?.let {
            val file = FileInformation().getFile(context, Uri.parse(it))
            val image = file.asRequestBody(TYPE_IMAGE.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(TYPE_IMAGE, file.name, image)
        }
        return null
    }
}
     