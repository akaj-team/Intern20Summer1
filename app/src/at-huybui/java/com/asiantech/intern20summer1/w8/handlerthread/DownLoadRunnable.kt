package com.asiantech.intern20summer1.w8.handlerthread

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DownLoadRunnable(private var downLoadHandlerThread: DownLoadHandlerThread) : Runnable {
    private var count = 0
    private var thread: Thread = Thread(this)
    private lateinit var url: URL
    private var nameFile = ""
    private var extension = ""
    override fun run() {
        val outputStream: OutputStream
        try {
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()
            val length = connection.contentLength
            if (length > 0) {
                val inputStream = connection.inputStream
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val path = File(directory, "$nameFile$extension")
                outputStream = FileOutputStream("$path")
                val data = ByteArray(length)
                var total = 0
                var count = inputStream?.read(data)
                Log.d("XXXX", "cacth ae")
                while (count != -1) {
                    // allow canceling with back button
                    count?.let {
                        total += it
                        outputStream.write(data, 0, it)
                    }
                    // publishing the progress....
                    val progress = ((total / length.toFloat()) * 100).toInt()
                    downLoadHandlerThread.sendOrder(progress)
                    if (progress == 100) {
                        downLoadHandlerThread.quit()
                    } else {
                        count = inputStream?.read(data)
                    }
                }
            }
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
            Log.d("XXXX", "cacth nfe")
        } catch (ae: ArithmeticException) {
            Log.d("XXXX", "cacth ae")
            ae.printStackTrace()
        } catch (ie: IllegalArgumentException) {
            Log.d("XXXX", "cacth ie")
            ie.printStackTrace()
        }
    }

    fun setParams(urlParam: String = "", nameParam: String = "", extensionParams: String = "") {
        url = URL(urlParam)
        nameFile = nameParam
        extension = extensionParams
    }

    fun start() {
        if (!thread.isAlive)
            thread.start()
    }
}
