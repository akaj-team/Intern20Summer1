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
        internal const val START_PROGRESS = 100
        internal const val FILE_SIZE = 1024
        internal const val SIZE = 8192
    }

    override fun run() {
        var count: Int
        var total = 0
        val url = URL(path)
        val connection = url.openConnection()
        connection.connect()

        val lengthOfFile = connection.contentLength
        val input: InputStream = BufferedInputStream(url.openStream(), SIZE)
        val output =
            FileOutputStream(File("${context.getExternalFilesDir(null)}/${Date().time}.jpg"))
        val data = ByteArray(FILE_SIZE)
        while (input.read(data).also { count = it } != -1) {
            total += count
            output.write(data, 0, count)
            val process = (total * START_PROGRESS) / lengthOfFile
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
