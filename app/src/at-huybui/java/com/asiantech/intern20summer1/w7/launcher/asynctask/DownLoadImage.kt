package com.asiantech.intern20summer1.w7.launcher.asynctask

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log.d
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class DownLoadImage(context: Context, private var name: String) :
    AsyncTask<String, Unit, String>() {
    private var mContext: WeakReference<Context> = WeakReference(context)
    private var directory: File? = null

    override fun doInBackground(vararg params: String?): String? {
        val url = params[0]
        val requestOptions = RequestOptions().override(500, 500)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            try {
                directory = it.getDir("imagePlants", Context.MODE_PRIVATE)
                val path = File(directory, "$name.jpg")
                val out = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                d("Seiggailion", "Image saved.")
            } catch (e: Exception) {
                d("Seiggailion", "Failed to save image.")
            }
        }
        return directory?.absolutePath.toString()
    }
}
