package com.asiantech.intern20summer1.test

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.`at-huybui`.activity_test.*
import java.io.*


class TestActivity : AppCompatActivity() {

    private var pathInStore: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btnLoadUrl.setOnClickListener {
            val url = edtTestUrl.text.toString()
            val name = edtTestName.text.toString()
            loadImageFromUrlToInternalStore(this@TestActivity, url, name)
        }


        btnLoadStore.setOnClickListener {
            val name = edtTestName?.text.toString()
            pathInStore?.let { it1 -> loadImageFromStorage(it1, name) }
        }

        btnClear?.setOnClickListener {
            imgTest?.setImageResource(R.drawable.logo_music)
        }
    }

    private fun loadImageFromUrlToInternalStore(
        context: Context,
        urlImage: String,
        namePlant: String
    ) {
        Glide.with(context)
            .asBitmap()
            .load(urlImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    saveToInternalStorage(bitmap, namePlant)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, imageName: String) {
        val cw = ContextWrapper(applicationContext)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        pathInStore = directory.absolutePath
        val mypath = File(directory, "$imageName.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun loadImageFromStorage(path: String, namePlant: String) {
        try {
            val f = File(path, "$namePlant.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            imgTest.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}