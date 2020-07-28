package com.asiantech.intern20summer1.w4.fragment

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.takusemba.cropme.OnCropListener
import kotlinx.android.synthetic.`at-huybui`.fragment_crop_image.*
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream

class CropImageFragment : Fragment() {

    companion object {
        private const val NAME_IMAGE = "/image/crop"
        private const val SDK_VERSION_SAVE_IMAGE = 29
        private const val DELAYS_CROP_PICTURE = 1000L
        private const val MILLIS = 1000
        private const val QUALITY = 100
        internal fun newInstance(uri: Uri?) = CropImageFragment().apply {
            uriBuf = uri
        }
    }

    private var toastStatus: Toast? = null
    private var uriBuf: Uri? = null
    internal var onCropImage: (Uri?) -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crop_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cropView.setUri(uriBuf as Uri) // init image enter to frame
        initListener()

    }

    private fun initListener() {
        cropView.addOnCropListener(object : OnCropListener {
            override fun onSuccess(bitmap: Bitmap) {
                val uriImage = saveImage(bitmap,context as Context)
                Handler().postDelayed({
                    progressBarCropPicture.visibility = View.INVISIBLE
                    onCropImage(uriImage)
                    fragmentManager?.popBackStack()
                }, DELAYS_CROP_PICTURE)
            }

            override fun onFailure(e: Exception) {
                showToast("Crop error")
                fragmentManager?.popBackStack()
            }
        })
        cropView.isOffFrame() // optionally check if the image is off of the frame.

        btnOkCrop.setOnClickListener(View.OnClickListener {
            if (cropView.isOffFrame()) {
                return@OnClickListener
            }
            progressBarCropPicture.visibility = View.VISIBLE
            cropView.crop()
        })

        btnBackCrop.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    /**
     * Functions save image to gallery
     */
    @Suppress("DEPRECATION")
    private fun saveImage(bitmap: Bitmap,context: Context): Uri? {
        val uri: Uri?
        if (android.os.Build.VERSION.SDK_INT >= SDK_VERSION_SAVE_IMAGE) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.
             uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
        } else {
            val directory =
                File(Environment.getExternalStorageDirectory().toString() + separator + NAME_IMAGE)
            // getExternalStorageDirectory is deprecated in API 29
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            val values = contentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        }
        return uri
    }

    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / MILLIS)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, outputStream)
                outputStream.close()
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            } catch (ae: ArithmeticException) {
                ae.printStackTrace()
            } catch (iae: IllegalArgumentException) {
                iae.printStackTrace()
            }
        }
    }

    /**
     * This function will show a toast on display
     */
    private fun showToast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        toastStatus?.cancel()
        toastStatus = Toast.makeText(context, text.toString(), duration).apply { show() }
    }
}
