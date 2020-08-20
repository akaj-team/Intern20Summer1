package com.asiantech.intern20summer1.week8.asynctask

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.asiantech.intern20summer1.week8.handler.DownloadHandlerThread
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.*

class DownloadAsyncTask(
    private val context: Context,
    private val progressBar: ProgressBar,
    private val tvDisplayCurrentProgress: TextView
) : AsyncTask<String, String, String>() {

    companion object{
        private const val HUNDRED = 100
    }

    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context, "Starting download..", Toast.LENGTH_SHORT).show()
    }

    override fun doInBackground(vararg params: String?): String {
        var count: Int
        var total = 0
        val url = URL(params[0])
        val connection = url.openConnection()
        connection.connect()
        val lengthFile = connection.contentLength
        val input: InputStream = BufferedInputStream(url.openStream(), DownloadHandlerThread.BUFFER_SIZE)
        val output =
            FileOutputStream(File("${context.getExternalFilesDir(null)}/${Date().time}.jpg"))
        val data = ByteArray(DownloadHandlerThread.FILE_SIZE)
        while (input.read(data).also { count = it } != -1) {
            total += count
            output.write(data, 0, count)
            publishProgress("${(total * HUNDRED) / lengthFile}")
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
        tvDisplayCurrentProgress.text = values[0]
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Uri.parse(result)
        Toast.makeText(context, "Download image successfully!", Toast.LENGTH_SHORT).show()
    }
}