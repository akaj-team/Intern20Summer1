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

class DownLoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load)
        initListener()
    }

    private fun initListener() {
        btnAsyncTask?.setOnClickListener {

            /**
             * https://r8---sn-8pxuuxa-q5q6.googlevideo.com/videoplayback?expire=1597701785&ei=Oao6X9btJJq24QKm-664CA&ip=2402%3A800%3A621c%3Aeb44%3A304f%3A3449%3Ab8da%3A6e8b&id=o-APssU5WYP9mLlSxDmGjZsheHoEbkzVc0ksNfLD-OSmq5&itag=22&source=youtube&requiressl=yes&mh=II&mm=31%2C29&mn=sn-8pxuuxa-q5q6%2Csn-npoeenl7&ms=au%2Crdu&mv=m&mvi=8&pcm2cms=yes&pl=48&gcr=vn&initcwndbps=711250&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=2814.049&lmt=1595124863041265&mt=1597680046&fvip=3&fexp=23883098&c=WEB&txp=5535432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cgcr%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgSajLEjIy1C1bmxFCbIzkecZwaKT-yyjOuyrrUcXDPG4CIEewZ236IJNS0l-NrrFtFxC2f8lEvATWbyo51dX8ZwKR&sig=AOq0QJ8wRgIhALdusV0jom4wDtE27tMECJNsW13RVG1RSx0FpgFXa5-DAiEAlPkS4_VkfgwLAYZ_Dxfp-JrZy2ljr1BMoH5q7RJcnvo%3D
             */
            val url = edtUrl.text.toString()
            val name = edtNameFile.text.toString()
            val extension = edtExtension.text.toString()
            DownLoadAsyncTask().execute(url, name, extension)
        }
    }

    inner class DownLoadAsyncTask : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg params: String?): String? {
            val outputStream: OutputStream
            try {
                val url = URL(params[0])
                val nameFile = params[1]
                val extension = params[2]
                d("XXXX", " URL ${params[0]}")
                val connection = url.openConnection() as HttpsURLConnection
                connection.connect()
                val input = connection.inputStream
                val directory = getExternalFilesDir(null)
                val path = File(directory, "$nameFile$extension")
                outputStream = FileOutputStream("${path}")
                d("XXXX", " Uri ${path}")
                val length = connection.contentLength
                d("XXXX", length.toString())
                if (length > 0) {
                    val data = ByteArray(length)
                    d("XXXX", length.toString())
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
                        d("XXXX", "[async]: total = $total\t\t: leng = $length")
                        publishProgress(((total/ length.toFloat()) * 100).toInt())
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
            values[0]?.let { progressBar?.progress = it }
            tvProgress?.text = "${values[0]}%"
        }
    }
}
