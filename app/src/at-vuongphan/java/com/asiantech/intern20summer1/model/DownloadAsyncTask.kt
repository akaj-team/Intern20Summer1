package com.asiantech.intern20summer1.model

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.*


@Suppress("DEPRECATION")
class DownloadAsyncTask(
    private val context: Context,
    private val progressBar: ProgressBar,
    private val tvRatioNumber: TextView
) : AsyncTask<String, String, String>() {
    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context, "Starting download", Toast.LENGTH_SHORT).show()
    }

    override fun doInBackground(vararg params: String?): String {
        var count: Int
        var total = 0
        val url = URL(params[0])
        val connection = url.openConnection()
        connection.connect()
        val lengthFile = connection.contentLength
        val input: InputStream = BufferedInputStream(url.openStream(), DownloadHandlerThread.SIZE)
        val output =
            FileOutputStream(File("${context.getExternalFilesDir(null)}/${Date().time}.jpg"))
        val data = ByteArray(DownloadHandlerThread.FILE_SIZE)
        while (input.read(data).also { count = it } != -1) {
            total += count
            output.write(data, 0, count)
            publishProgress("${(total * DownloadHandlerThread.START_PROGRESS) / lengthFile}")
        }
        output.flush()
        output.close()
        input.close()
        return ""
    }

    override fun onProgressUpdate(vararg values: String?) {
        super.onProgressUpdate(*values)
        values[0]?.let {
            progressBar.progress = it.toInt()
        }
        tvRatioNumber.text = values[0]
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Uri.parse(result)
        Toast.makeText(context, "Download done", Toast.LENGTH_SHORT).show()
    }
}
