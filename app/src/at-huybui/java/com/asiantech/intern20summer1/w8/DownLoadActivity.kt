package com.asiantech.intern20summer1.w8

import android.content.Intent
import android.net.Uri
import android.os.*
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_down_load.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class DownLoadActivity : AppCompatActivity() {

    companion object {
        private const val REGEX_URL =
            """(https:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?"""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load)
        initListener()
    }

    private fun initListener() {
        btnAsyncTask?.setOnClickListener {
            val url = edtUrl.text.toString()
            val name = edtNameFile.text.toString()
            val extension = edtExtension.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this@DownLoadActivity, "Hãy nhập tên file", Toast.LENGTH_SHORT)
                    .show()
            } else if (extension.isEmpty()) {
                Toast.makeText(this@DownLoadActivity, "Hãy nhập extension", Toast.LENGTH_SHORT)
                    .show()
            } else if (!url.matches(REGEX_URL.toRegex())) {
                Toast.makeText(this@DownLoadActivity, "Url không hợp lệ", Toast.LENGTH_SHORT).show()
            } else {
                DownLoadAsyncTask().execute(url, name, extension)
            }
        }

        btnOpenFile?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val directory = Environment.DIRECTORY_DOWNLOADS
            d("XXXX", "[path]: $directory")
            val uri: Uri = Uri.parse(directory) // a directory
            intent.setDataAndType(uri, "*/*")
            startActivity(intent)
        }
    }

    inner class DownLoadAsyncTask : AsyncTask<String, Int, Int>() {
        @Suppress("DEPRECATION")
        override fun doInBackground(vararg params: String?): Int? {
            val outputStream: OutputStream
            try {
                val url = URL(params[0])
                val nameFile = params[1]
                val extension = params[2]
                val connection = url.openConnection() as HttpsURLConnection
                connection.connect()
                val length = connection.contentLength
                if (length > 0) {
                    val inputStream = connection.inputStream
                    val directory =
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val path = File(directory, "$nameFile$extension")
                    outputStream = FileOutputStream("$path")
                    val data = ByteArray(length)
                    if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                        return 0
                    }
                    var total = 0
                    var count = inputStream?.read(data)
                    d("XXXX", "cacth ae")
                    if (count == -1) {
                        return -1
                    }
                    while (count != -1) {
                        // allow canceling with back button
                        if (isCancelled) {
                            inputStream?.close()
                            return null
                        }
                        count?.let {
                            total += it
                            outputStream.write(data, 0, it)
                        }
                        // publishing the progress....
                        publishProgress(((total / length.toFloat()) * 100).toInt())
                        count = inputStream?.read(data)
                    }
                }
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
                d("XXXX", "cacth nfe")
            } catch (ae: ArithmeticException) {
                d("XXXX", "cacth ae")
                ae.printStackTrace()
            } catch (ie: IllegalArgumentException) {
                d("XXXX", "cacth ie")
                ie.printStackTrace()
            }
            return -1
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                progressBar?.progress = it
                val text = "$it%"
                tvProgress?.text = text
            }
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            when (result) {
                0 -> {
                    val text = getString(R.string.w8_server_not_ok)
                    Toast.makeText(this@DownLoadActivity, text, Toast.LENGTH_SHORT).show()
                }
                -1 -> {
                    d("XXXX", "dont download")
                    val text = getString(R.string.w8_dont_download)
                    Toast.makeText(this@DownLoadActivity, text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private class UiHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

        }
    }

    private class MyHandlerThread(private var uiHandler: DownLoadActivity.UiHandler) :
        HandlerThread("") {
        var handler: Handler? = null

        override fun onLooperPrepared() {
            super.onLooperPrepared()
            handler = getHandler(looper)
        }

        override fun run() {
            super.run()
            orderHandlerThread.sendOrder()
            //4
            try {
                Thread.sleep(1000)
            } catch (exception: InterruptedException) {
                exception.printStackTrace()
            }
        }

        private fun getHandler(looper: Looper): Handler {
            //1
            return object : Handler(looper) {
                //2
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    //6
                    val processedMessage = Message()
                    uiHandler.sendMessage(processedMessage)
                }
            }
        }

        fun sendOrder() {
            //1
            val message = Message()
            //2
            message.obj = foodOrder
            //3
            handler?.sendMessage(message)
        }
    }
}
