package com.asiantech.intern20summer1.w8

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_down_load.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@Suppress("DEPRECATION")
class DownLoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load)
        initListener()
    }

    private fun initListener() {
        btnAsyncTask?.setOnClickListener {
            val url = edtUrl.text.toString()
            DownLoadAsyncTask().execute(url)
        }
    }

    inner class DownLoadAsyncTask : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg params: String?): String? {
            val outputStream: OutputStream
            try {
                val url = URL(params[0])
                d("XXXX", " URL ${params[0]}")
                val connection = url.openConnection() as HttpsURLConnection
                connection.connect()
                val input = connection.inputStream
                d("XXXX", " ")
                outputStream = FileOutputStream("/path")
                val length = connection.contentLength
                if (length > 0) {
                    val data = ByteArray(1000000)
                    if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                        d("XXXX", "   return HTTP")
                        return "Server return HTTP" + (connection.responseCode
                            .toString() + " " + connection.responseMessage)
                    }
                    var count = input?.read(data)
                    var total = 0
                    while (count != -1) {
                        // allow canceling with back button
                        if (isCancelled) {
                            d("XXXX", "[file] close")
                            input?.close()
                            return null
                        }
                        if (count != null) {
                            total += count
                        }
                        // publishing the progress....
                        d("XXXX", "[async]- publish")
                        publishProgress(total)
                        count?.let { outputStream.write(data, 0, it) }
                        count = input?.read(data)
                    }
                }
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
                d("XXXX", "[Catch]: Tính toán số học")
            } catch (ae: ArithmeticException) {
                ae.printStackTrace()
                d("XXXX", "[Catch]:Không phải số")
            } catch (ie: IllegalArgumentException) {
                ie.printStackTrace()
                d("XXXX", "[Catch]: Đối số không phù hợp")
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            d("XXXX", "[value]- ${values[0]}")
            values[0]?.let { progressBar?.progress = it }
            tvProgress?.text = "${values[0]}"
        }
    }
}
