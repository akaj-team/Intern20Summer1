package com.asiantech.intern20summer1.fragment.w10

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-vuongphan`.w10_add_new_feed.*

class Update : Fragment() {
    companion object {
        private const val ID = "id"
        private const val CONTENT = "content"
        private const val IMAGE = "image"
        internal fun newInstance(id: Int, content: String, image: String) = Update().apply {
            arguments = Bundle().apply {
                putInt(ID, id)
                putString(CONTENT, content)
                putString(IMAGE, image)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_add_new_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(ID)
        val content = arguments?.getString(CONTENT)
        val image = arguments?.getString(IMAGE)
        edtNoidung.setText(content)
        Log.d("TAG", "onViewCreated: ${Uri.parse(image)}")
        Log.d("TAG", "path: ${getPath(Uri.parse(image))}")
    }

    private fun getPath(uri: Uri?): String? {
        val result: String
        val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (cursor == null) {
            result = uri?.path.toString()
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}