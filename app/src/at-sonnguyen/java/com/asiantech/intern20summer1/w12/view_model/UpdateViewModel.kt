package com.asiantech.intern20summer1.w12.view_model

import androidx.lifecycle.ViewModel
import com.asiantech.intern20summer1.w12.api.APIClient
import com.asiantech.intern20summer1.w12.model.PostContent
import okhttp3.MultipartBody

class UpdateViewModel : ViewModel() {
    internal fun update(
        token: String?,
        id: Int,
        image: MultipartBody.Part?,
        postContent: PostContent
    ) = APIClient.createPostService()?.updatePost(token, id, image, postContent)
}
