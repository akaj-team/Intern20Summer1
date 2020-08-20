package com.asiantech.intern20summer1.w8.handlerthread

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui
 * This is Runnable class used to down load file
 */

@Suppress("DEPRECATION")
class DownLoadRunnable(private var downLoadHandlerThread: DownLoadHandlerThread) : Runnable {

    companion object {
        private const val PERCENTAGE = 100
    }

    private var thread: Thread = Thread(this)
    private lateinit var url: URL
    private var nameFile = ""
    private var extension = ""

    override fun run() {
        try {
            handleStartDownLoad()
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        } catch (ae: ArithmeticException) {
            ae.printStackTrace()
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        }
    }

    fun setParams(urlParam: String, nameParam: String, extensionParams: String) {
        url = URL(urlParam)
        nameFile = nameParam
        extension = extensionParams
    }

    fun start() {
        if (!thread.isAlive) {
            thread.start()
        }
    }

    private fun handleStartDownLoad() {
        val connection = url.openConnection() as HttpsURLConnection
        connection.connect()
        val length = connection.contentLength
        if (length <= 0) {
            downLoadHandlerThread.sendProgress(-1)
            downLoadHandlerThread.quit()
        } else {
            val outputStream: OutputStream
            val inputStream = connection.inputStream
            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val path = File(directory, "$nameFile$extension")
            outputStream = FileOutputStream("$path")
            val data = ByteArray(length)
            var total = 0
            var count = inputStream?.read(data)
            while (count != -1) {
                // allow canceling with back button
                count?.let {
                    total += it
                    outputStream.write(data, 0, it)
                }
                // publishing the progress....
                val progress = ((total / length.toFloat()) * PERCENTAGE).toInt()
                downLoadHandlerThread.sendProgress(progress)
                count = inputStream?.read(data)
            }
        }
    }
}
