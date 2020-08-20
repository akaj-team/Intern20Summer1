package com.asiantech.intern20summer1.week8

import android.content.Context
import android.os.Handler
import android.os.Message
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.FILE_NAME
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.FILE_TAIL
import com.asiantech.intern20summer1.week8.DownloadFileActivity.Companion.TIME_PERIOD
import java.io.*
import java.net.MalformedURLException
import java.net.URL

class ThreadHandler(val context: Context, val imageUrl: String, val handler: Handler) :
    Runnable {
    private var directory: File? = null
    override fun run() {
        try {
            directory = context.getDir(FILE_NAME, Context.MODE_PRIVATE)
            val path = File(
                directory,
                "${FILE_NAME}${FILE_TAIL}"
            )
            val url = URL(imageUrl)
            val urlConnection = url.openConnection()
            var total = 0
            var count = 0
            urlConnection.connect()
            val fileLength = urlConnection.contentLength
            val input: InputStream = BufferedInputStream(url.openStream())
            val output = FileOutputStream(path)
            val data = ByteArray(DownloadFileActivity.BYTE_ARRAY_SIZE)
            while (input.read(data).let {
                    count = it
                    it != -1
                }) {
                total += count
                output.write(data, 0, count)
                val msg = Message()
                msg.what = TIME_PERIOD
                msg.arg1 = (total * TIME_PERIOD / fileLength)
                handler.sendMessage(msg)
            }
            output.flush()
            output.close()
            input.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace();
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}
