package com.asiantech.intern20summer1.download

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
    private val path: String,
    private val handler: Handler
) : Runnable {

    companion object {
        private const val START_PROGRESS = 100
    }

    override fun run() {
        var count: Int
        var total = 0
        val url = URL(path)
        val connection = url.openConnection()
        connection.connect()

        val lengthOfFile = connection.contentLength
        val input: InputStream = BufferedInputStream(url.openStream(), 8192)
        val output =
            FileOutputStream(File("${context.getExternalFilesDir(null)}/${Date().time}.jpg"))
        val data = ByteArray(1024)
        while (input.read(data).also { count = it } != -1) {
            total += count
            output.write(data, 0, count)
            val process = (total * 100) / lengthOfFile
            val msg = Message()
            msg.what = START_PROGRESS
            msg.arg1 = process
            handler.sendMessage(msg)
        }
        output.flush()
        output.close()
        input.close()
    }
}
