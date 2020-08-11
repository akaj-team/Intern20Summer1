package com.asiantech.intern20summer1.w7.launcher.asynctask

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import com.asiantech.intern20summer1.w7.companion.AppCompanion
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class DownLoadImage(context: Context, private var name: String) :
    AsyncTask<String, Unit, String>() {

    companion object {
        private const val SIZE_IMAGE = 500
        private const val QUALITY_IMAGE = 100
    }

    private var context: WeakReference<Context> = WeakReference(context)
    private var directory: File? = null

    override fun doInBackground(vararg params: String?): String? {
        val url = params[0]
        val requestOptions = RequestOptions().override(SIZE_IMAGE, SIZE_IMAGE)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        context.get()?.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            try {
                directory = it.getDir(AppCompanion.NAME_DIR, Context.MODE_PRIVATE)
                val path = File(directory, "$name${AppCompanion.FILE_TAIL}")
                val out = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMAGE, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
            }
        }
        return directory?.absolutePath.toString()
    }
}
