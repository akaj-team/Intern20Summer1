package com.asiantech.intern20summer1.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableHomeActivity
import com.asiantech.intern20summer1.data.Constants.Companion.OPTION_CANCEL
import com.asiantech.intern20summer1.data.Constants.Companion.OPTION_CHOOSE_GALLERY
import com.asiantech.intern20summer1.data.Constants.Companion.OPTION_TAKE_PHOTO
import com.asiantech.intern20summer1.data.VegetableUser
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.textChangedListener
import kotlinx.android.synthetic.`at-vuongphan`.w7_register_fragment.*
import java.io.ByteArrayOutputStream
import java.io.Serializable

@Suppress("DEPRECATION")
class VegetableRegisterFragment : Fragment() {
    private var pathImage: String? = null

    companion object {
        internal fun newInstance(): VegetableRegisterFragment {
            return VegetableRegisterFragment()
        }

        private const val REQUEST_IMAGE_CAPTURE = 111
        private const val REQUEST_GET_CONTENT_IMAGE = 222
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        imgAvatar.setOnClickListener {
            dispatchPictureIntent(requireContext())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imgAvatar.setImageBitmap(imageBitmap)
                    val projection = arrayOf(MediaStore.Images.Media.DATA)
                    val imageUri = imageBitmap?.let { getImageUri(it) }
                    val cursor = imageUri?.let {
                        requireContext().contentResolver.query(
                            it,
                            projection,
                            null,
                            null,
                            null
                        )
                    }
                    val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor?.moveToFirst()
                    pathImage = index?.let { cursor.getString(it) }.toString()
                }
                else -> {
                    imgAvatar.setImageURI(data?.data)
                    val projection = arrayOf(MediaStore.Images.Media.DATA)
                    val uri = data?.data
                    val cursor = uri?.let {
                        requireContext().contentResolver.query(
                            it,
                            projection,
                            null,
                            null,
                            null
                        )
                    }
                    val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor?.moveToFirst()
                    pathImage = index?.let { cursor.getString(it) }.toString()
                }
            }
        }
    }

    private fun initListener() {
        initEnableButton()
        initListenerHideKeyboardLogin()
        initButtonNext()
    }

    private fun initButtonNext() {
        btnNext.setOnClickListener {
            val intent = Intent(context, VegetableHomeActivity::class.java)
            val name = edtUserName?.text.toString()
            val university = edtUniversity?.text.toString()
            val imgUri = imgAvatar?.toString()
            val user = VegetableUser(name, university)
            intent.putExtra("image", user as? Serializable)
            startActivity(intent)
            (activity as? Activity)?.finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        rlRegister?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun isCorrectFormat() =
        edtUserName.text.toString().isNotEmpty() && edtUniversity.text.toString().isNotEmpty() &&
                edtHome.text.toString().isNotEmpty()

    private fun initEnableButton() {
        initTextChangeListener(edtHome)
        initTextChangeListener(edtUserName)
        initTextChangeListener(edtUniversity)
    }

    private fun initTextChangeListener(editText: EditText) {
        editText.textChangedListener(onTextChanged = { _, _, _, _ ->
            btnNext?.apply {
                isEnabled = isCorrectFormat()
                if (isCorrectFormat()) {
                    background = resources.getDrawable(R.drawable.bg_register_button_radius_enable)
                }
            }
        })
    }

    private fun dispatchPictureIntent(context: Context) {
        val options = arrayOf(OPTION_TAKE_PHOTO, OPTION_CHOOSE_GALLERY, OPTION_CANCEL)
        AlertDialog.Builder(context).apply {
            setTitle("Choose your profile picture")
            setItems(options) { dialog, item ->
                when (options[item]) {
                    OPTION_TAKE_PHOTO -> {
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                            takePictureIntent.resolveActivity(context.packageManager)?.also {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                            }
                        }
                    }
                    OPTION_CHOOSE_GALLERY -> {
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        ).also { takePictureIntent ->
                            takePictureIntent.resolveActivity(context.packageManager)?.also {
                                startActivityForResult(takePictureIntent, REQUEST_GET_CONTENT_IMAGE)
                            }
                        }
                    }
                    OPTION_CANCEL -> dialog.dismiss()
                }
            }
        }.create().show()
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}
