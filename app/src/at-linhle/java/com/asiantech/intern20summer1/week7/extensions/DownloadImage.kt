package com.asiantech.intern20summer1.week7.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadImage(val context: Context, private var name: String) :
    AsyncTask<String, Unit, String>() {

    companion object {
        internal const val NAME_FILE_IMAGE_KEY = "imagePlants"
        internal const val FILE_TAIL_KEY = ".jpg"
        private const val QUALITY_IMAGE = 100
    }

    private var directory: File? = null

    override fun doInBackground(vararg params: String?): String? {
        val url = URL(params[0])
        val inputStream = url.openConnection()?.getInputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        try {
            directory = context.getDir(NAME_FILE_IMAGE_KEY, Context.MODE_PRIVATE)
            val path = File(directory, "$name${FILE_TAIL_KEY}")
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
        return directory?.absolutePath.toString()
    }
}