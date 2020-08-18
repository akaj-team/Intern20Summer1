package com.asiantech.intern20summer1.week8.extentsions

import android.content.Context
import android.os.Handler
import android.os.Message
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class ThreadHandler(
    private val context: Context,
    private val imageUrl: String,
    private val handler: Handler
) : Runnable {

    companion object {
        internal const val NAME_FILE_IMAGE_KEY = "imageDownload"
        internal const val FILE_TAIL_KEY = ".jpg"
        internal const val TIME_PERIOD = 100
        internal const val BYTE_ARRAY_SIZE = 1024
    }

    private var directory: File? = null

    override fun run() {
        try {
            directory = context.getDir(NAME_FILE_IMAGE_KEY, Context.MODE_PRIVATE)
            val path = File(directory, "${Date().time}${FILE_TAIL_KEY}")
            var count: Int
            var total = 0
            val url = URL(imageUrl)
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
                val msg = Message()
                msg.what = TIME_PERIOD
                msg.arg1 = (total * TIME_PERIOD / lengthOfFile)
                handler.sendMessage(msg)
            }
            output.flush()
            output.close()
            input.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
