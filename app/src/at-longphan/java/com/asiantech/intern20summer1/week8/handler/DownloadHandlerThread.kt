package com.asiantech.intern20summer1.week8.handler

import android.content.Context
import android.os.Handler
import android.os.Message
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.*

class DownloadHandlerThread(
    private val context: Context,
    private val urlString: String,
    private val handler: Handler
) : Runnable {

    companion object {
        internal const val DOWNLOAD_IMAGE_MSG_CODE = 3508
        internal const val HUNDRED = 100
        internal const val FILE_SIZE = 1024
        internal const val BUFFER_SIZE = 8192
    }

    override fun run() {
        var count: Int
        var total = 0
        val url = URL(urlString)
        val connection = url.openConnection()
        connection.connect()

        val lengthOfFile = connection.contentLength
        val input: InputStream = BufferedInputStream(url.openStream(), BUFFER_SIZE)
        val output =
            FileOutputStream(File("${context.getExternalFilesDir(null)}/${Date().time}.jpg"))
        val data = ByteArray(FILE_SIZE)
        while (input.read(data).also { count = it } != -1) {
            total += count
            output.write(data, 0, count)
            val process = (total * HUNDRED) / lengthOfFile
            val msg = Message()
            msg.what = DOWNLOAD_IMAGE_MSG_CODE
            msg.arg1 = process
            handler.sendMessage(msg)
        }
        output.flush()
        output.close()
        input.close()
    }
}
