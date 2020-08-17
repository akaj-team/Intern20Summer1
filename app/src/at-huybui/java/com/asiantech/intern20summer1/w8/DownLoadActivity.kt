package com.asiantech.intern20summer1.w8

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_down_load.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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
            try {
                val url = URL(params[0])
                d("XXXX", " URL ${params[0]}")
                val connection = url.openConnection() as HttpsURLConnection
                connection.connect()
                val length = connection.contentLength
                var data = ByteArray(length)
                d("XXXX", " Length $length")
                publishProgress(length)
                if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                    d("XXXX", "   return HTTP")
                    return "Server return HTTP" + (connection.responseCode
                        .toString() + " " + connection.getResponseMessage())
                }
                var total = 0
                return null
            } catch (e: Exception) {
                d("XXXX", "   catch")
                e.printStackTrace();
                return "false"; //swallow a 404
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
//            values[0]?.let { progressBar?.progress = it }
            tvProgress?.text = "DM đéo chạy"
        }
    }
}
