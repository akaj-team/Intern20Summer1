@file:Suppress("DEPRECATION")

package com.asiantech.intern20summer1.w8.asynctask

import android.os.AsyncTask
import android.os.Environment
import android.widget.Toast
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w8.DownLoadActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DownLoadAsyncTask(private var downLoadActivity: DownLoadActivity) :
    AsyncTask<String, Int, Int>() {

    companion object {
        private const val PERCENTAGE = 100
    }

    override fun doInBackground(vararg params: String?): Int? {
        var result = 1
        val outputStream: OutputStream
        try {
            val url = URL(params[0])
            val nameFile = params[1]
            val extension = params[2]
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()
            val length = connection.contentLength
            if (length <= 0) {
                result = -1
            } else {
                val inputStream = connection.inputStream
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val path = File(directory, "$nameFile$extension")
                outputStream = FileOutputStream("$path")
                val data = ByteArray(length)
                if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                    result = 0
                }
                var total = 0
                var count = inputStream?.read(data)
                while (count != -1) {
                    // allow canceling with back button
                    count?.let {
                        total += it
                        outputStream.write(data, 0, it)
                    }
                    // publishing the progress....
                    publishProgress(((total / length.toFloat()) * PERCENTAGE).toInt())
                    count = inputStream?.read(data)
                }
            }
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        } catch (ae: ArithmeticException) {
            ae.printStackTrace()
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        }
        return result
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        values[0]?.let {
            downLoadActivity.uiAsyncTask(it)
        }
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        when (result) {
            0 -> {
                val text = downLoadActivity.getString(R.string.w8_server_not_ok)
                Toast.makeText(downLoadActivity, text, Toast.LENGTH_SHORT).show()
            }
            -1 -> {
                val text = downLoadActivity.getString(R.string.w8_dont_download)
                Toast.makeText(downLoadActivity, text, Toast.LENGTH_SHORT).show()
            }
            1 -> {
                val text = downLoadActivity.getString(R.string.w8_ok_download)
                Toast.makeText(downLoadActivity, text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
