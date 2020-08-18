package com.asiantech.intern20summer1.week8

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.activity_download_progress_bar.*
import java.io.*
import java.net.MalformedURLException
import java.net.URL

class DownloadProgressBar : AppCompatActivity() {
    private val imageUrl: String =
        "https://scontent.fdad3-2.fna.fbcdn.net/v/t1.0-9/45145373_1161820700625274_7863066434337767424_n.jpg?_nc_cat=105&_nc_sid=13bebb&_nc_ohc=qy-UC-7P_RwAX_UYHAW&_nc_ht=scontent.fdad3-2.fna&oh=a1d6a0b598da2baf395ee48db8a36023&oe=5F60DA4D"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_progress_bar)

        handleDownloadButtonListener()
    }

    private fun handleDownloadButtonListener() {
        btnDownload.setOnClickListener {
            val downloadAsyncTask = DownloadFile(this)
            downloadAsyncTask.execute(imageUrl)
        }
    }

    private class DownloadFile(context: Activity) :
        AsyncTask<String, Int, String>() {
        private val parentContext: Activity = context

        override fun doInBackground(vararg params: String?): String? {
            try {
                val url = URL(params[0])
                val connection = url.openConnection()
                connection.connect()

                val fileLength = connection.contentLength
                val filePath = Environment.getExternalStorageDirectory().path
                val input: InputStream = BufferedInputStream(url.openStream())
                val output: OutputStream = FileOutputStream("$filePath/image.jpg")
                val data: ByteArray = ByteArray(1024)
                var total: Long = 0
                var count: Int

                while (input.read(data).let {
                        count = it
                        it != -1
                    }) {
                    total += count
                    publishProgress((total * 100 / fileLength).toInt())
                    output.write(data, 0, count)
                }

                output.flush()
                output.close()
                input.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                parentContext.progressBar.progress = it
                parentContext.tvPercent.text = "$it %"
            }
        }
    }
}
