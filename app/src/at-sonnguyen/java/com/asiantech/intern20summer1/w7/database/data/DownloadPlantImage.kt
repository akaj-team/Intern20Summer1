package com.asiantech.intern20summer1.w7.database.data

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class DownloadPlantImage(context: Context, private var name: String) :
    AsyncTask<String, Unit, String>() {

    companion object {
        internal const val FORMAT_CODE_DATE = "dd/MM/yyyy HH:mm"
        internal const val NAME_DIR = "imagePlants"
        internal const val FILE_TAIL = ".jpg"
        internal const val SECOND_IN_MINUTE = 60
        internal const val SECOND_IN_HOUR = 3600
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
                directory = it.getDir(NAME_DIR, Context.MODE_PRIVATE)
                val path = File(directory, "$name${FILE_TAIL}")
                val out = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMAGE, out)
                out.flush()
                out.close()
            } catch (ae: ArithmeticException) {
                ae.printStackTrace()
            } catch (ne: NumberFormatException) {
                ne.printStackTrace()
            } catch (ie: IllegalArgumentException) {
                ie.printStackTrace()
            }
        }
        return directory?.absolutePath.toString()
    }
}
