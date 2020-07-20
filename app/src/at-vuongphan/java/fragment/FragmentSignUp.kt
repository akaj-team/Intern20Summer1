package fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import extension.*
import kotlinx.android.synthetic.`at-vuongphan`.fragment_sign_up.*

class FragmentSignUp : Fragment() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val LENGTH_PHONE_NUMBER = 10
        const val REQUEST_GET_CONTENT_IMAGE = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlaceHolder(edtEmailSignUp_w4)
        initPlaceHolder(edtFullName_w4)
        initPlaceHolder(edtNumber_w4)
        initPlaceHolder(edtPasswordSignUp_w4)
        initPlaceHolder(edtPasswordConfirm_w4)
        initChooseImage()
        enableRegisterButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> if (resultCode == RESULT_OK) {
                imgIconName_w4.setImageBitmap(data?.extras?.get("data") as Bitmap)
            }
            REQUEST_GET_CONTENT_IMAGE -> if (resultCode == RESULT_OK) {
                imgIconName_w4.setImageURI(data?.data)
            }
        }
    }

    private fun initPlaceHolder(edit: EditText) {
        edit.hint = FragmentSignIn.HINT
    }

    private fun initChooseImage() {
        imgIconName_w4.setOnClickListener {
            dialogChooseImage()
        }
    }

    private fun dialogChooseImage() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Choose Action")
        builder.setMessage("Take photo from camera or gallery")
        builder.setPositiveButton("Camera") { _, _ ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
        builder.setNegativeButton("Gallery") { _, _ ->
            val image = Intent(Intent.ACTION_GET_CONTENT)
            image.type = "image/*"
            startActivityForResult(image, REQUEST_GET_CONTENT_IMAGE)
        }
        builder.show()
    }

    private fun isCorrectFormatSignUp(
        name: String,
        email: String,
        phone: String,
        password: String,
        typePassword: String
    ) = name.isFullName()
            && email.isValidEmail() && phone.isPhoneNumber() && password.isValidPasswordW4() && typePassword == password

    private fun enableRegisterButton() {
        edtFullName_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister_w4.isEnabled = isCorrectFormatSignUp(
                p0.toString(),
                edtEmailSignUp_w4.text.toString(),
                edtNumber_w4.text.toString(),
                edtPasswordSignUp_w4.text.toString(),
                edtPasswordConfirm_w4.text.toString()
            )
        })
        edtEmailSignUp_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister_w4.isEnabled = isCorrectFormatSignUp(
                edtFullName_w4.text.toString(),
                p0.toString(),
                edtNumber_w4.text.toString(),
                edtPasswordSignUp_w4.text.toString(),
                edtPasswordConfirm_w4.text.toString()
            )
        })
        edtNumber_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister_w4.isEnabled = isCorrectFormatSignUp(
                edtFullName_w4.text.toString(),
                edtEmailSignUp_w4.text.toString(),
                p0.toString(),
                edtPasswordSignUp_w4.text.toString(),
                edtPasswordConfirm_w4.text.toString()
            )
        })
        edtPasswordSignUp_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister_w4.isEnabled = isCorrectFormatSignUp(
                edtFullName_w4.text.toString(),
                edtEmailSignUp_w4.text.toString(),
                edtNumber_w4.text.toString(),
                p0.toString(),
                edtPasswordConfirm_w4.text.toString()
            )
        })
        edtPasswordSignUp_w4.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister_w4.isEnabled = isCorrectFormatSignUp(
                edtFullName_w4.text.toString(),
                edtEmailSignUp_w4.text.toString(),
                edtNumber_w4.text.toString(),
                edtPasswordSignUp_w4.text.toString(),
                p0.toString()
            )
        })
    }
}
