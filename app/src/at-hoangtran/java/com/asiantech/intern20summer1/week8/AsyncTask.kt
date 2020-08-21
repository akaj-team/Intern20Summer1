package com.asiantech.intern20summer1.week8

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.BYTE_ARRAY_SIZE
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.FILE_NAME
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.FILE_TAIL
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.TIME_PERIOD
import java.io.*
import java.net.MalformedURLException
import java.net.URL

class AsyncTask(
    private val context: Context,
    private val progressBar: ProgressBar,
    private val textView: TextView
) :
    AsyncTask<String, String, String>() {

    private var directory: File? = null
    override fun doInBackground(vararg params: String?): String? {
        try {
            directory = context.getDir(FILE_NAME, Context.MODE_PRIVATE)
            val path = File(directory, FILE_TAIL)
            val url = URL(params[0])
            val urlConnection = url.openConnection()
            var total = 0
            var count: Int
            urlConnection.connect()
            val fileLength = urlConnection.contentLength
            val input: InputStream = BufferedInputStream(url.openStream())
            val output = FileOutputStream(path)
            val data = ByteArray(BYTE_ARRAY_SIZE)
            while (input.read(data).let {
                    count = it
                    it != -1
                }) {
                total += count
                output.write(data, 0, count)
                publishProgress("${total * TIME_PERIOD / fileLength}")
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
    override fun onProgressUpdate(vararg values: String?) {
        values[0]?.let { progressBar.progress = it.toInt() }
        textView.text = values[0] + context.getString(R.string.percent)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Toast.makeText(context, R.string.download_complete, Toast.LENGTH_SHORT).show()
    }
}
