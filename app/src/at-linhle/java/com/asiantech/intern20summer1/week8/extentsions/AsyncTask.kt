package com.asiantech.intern20summer1.week8.extentsions

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.TextView
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week8.extentsions.ThreadHandler.Companion.BYTE_ARRAY_SIZE
import com.asiantech.intern20summer1.week8.extentsions.ThreadHandler.Companion.FILE_TAIL_KEY
import com.asiantech.intern20summer1.week8.extentsions.ThreadHandler.Companion.NAME_FILE_IMAGE_KEY
import com.asiantech.intern20summer1.week8.extentsions.ThreadHandler.Companion.TIME_PERIOD
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class AsyncTask(
    val context: Context,
    private val progressBar: ProgressBar,
    private val textView: TextView
) :
    AsyncTask<String, Int, String>() {

    private var directory: File? = null

    override fun doInBackground(vararg params: String?): String? {
        try {
            directory = context.getDir(NAME_FILE_IMAGE_KEY, Context.MODE_PRIVATE)
            val path = File(directory, "${Date().time}${FILE_TAIL_KEY}")
            var count: Int
            var total = 0
            val url = URL(params[0])
            val connection = url.openConnection()
            connection.connect()

            val lengthOfFile = connection.contentLength

            val input: InputStream = BufferedInputStream(url.openStream())
            val output = FileOutputStream(path)
            val data = ByteArray(BYTE_ARRAY_SIZE)
            while (input.read(data).let {
                    count = it
                    it != -1
                }) {
                total += count
                output.write(data, 0, count)
                publishProgress(total * TIME_PERIOD / lengthOfFile)
            }

            output.flush()
            output.close()
            input.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @SuppressLint("SetTextI18n")
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        values[0]?.let { progressBar.progress = it }
        textView.text = values[0].toString() + context.getString(R.string.download_image_activity_percent_description)
    }
}
