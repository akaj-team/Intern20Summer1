package com.asiantech.intern20summer1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-huybui`.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mToast: Toast? = null // Khai báo cho toast
    private val regexEmail = """^[a-z][a-z0-9_.]{5,32}[@][a-z0-9]{2,}(.[a-z0-9]{2,4}){1,2}${'$'}""".toRegex() // regex cho email
    private val regexPass =
        """(?=^.{8,}${'$'})((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*${'$'}""".toRegex() // regex cho mật khẩu

    private var mBufEmail = ""          // biến đệm lưu trữ email hợp lệ
    private var mBufPass = ""           // biến đệm lưu trư mật khẩu hơp lệ
    private var mFlatRepass = false     // biến đệm lưu trangj thái xác nhận mật khẩu hợp lệ
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ẩn các image view tick trong edit text
        imgTickEmail.visibility = View.INVISIBLE
        imgTickPass.visibility = View.INVISIBLE
        imgTickRepass.visibility = View.INVISIBLE

        // gọi các hàm xử lý của các view
        handleForEdittextEmail()
        handleForEdittextPass()
        handleForEdittextRePass()
        handleForlistener()


    }

    /**
     * hàm xử lý cho các setOnCLicklistener của các nút nhấn
     * bên trong hàm có:
     *     - button sign up
     *     - image view facebook, twitter, google
     *     - text register, sign up
     * hàm không có tham số đầu vào và không trả về giá trị
     */

    private fun handleForlistener() {
        btnSignup.setOnClickListener {
            val textEmail = edtEmail.text.toString()   // lấy text trong edit text email
            val textPass = edtPass.text.toString()    // lấy text trong edit text pass
            val textRepass = edtRePass.text.toString()  // lấy text trong edit text repass
            if (textEmail.isEmpty() || textPass.isEmpty() || textRepass.isEmpty()) { // Kiểm tra xem có edit text nào trống hay không
                val text =
                    resources.getString(R.string.text_enter_full_email_and_password)  // lấy text trong string để hiển thị
                text.toast() // hiển thị lên toast là có ô trống
            } else if (mBufEmail.isEmpty() || mBufPass.isEmpty() || !mFlatRepass) {  // nếu không có ô nào trống thì kiểm tra xem email và mật khẩu có hợp lệ không
                val text = resources.getString(R.string.text_email_or_password_is_invalid)
                text.toast()
            } else {
                "Email    : $mBufEmail\nPassword: $mBufPass".toast() // Không vấn đề gì thì hiện email và pass
            }
        }

        tvRegister?.setOnClickListener {
            resources.getString(R.string.text_goto_register).toast()
        }
        tvSigup?.setOnClickListener {
            resources.getString(R.string.text_goto_register).toast()
        }
        imgFacebook?.setOnClickListener {
            resources.getString(R.string.text_goto_facebook).toast()
        }
        imgTwitter?.setOnClickListener {
            resources.getString(R.string.text_goto_twitter).toast()
        }
        imgGoogle?.setOnClickListener {
            resources.getString(R.string.text_goto_google).toast()
        }


    }

    /**
     * hàm xử lý sự kiện cho edit text email
     * hàm kiểm tả trạng thái của edit text: trống hay không trống, email nhập có hơpj thệ hay không.
     * điều khiển ẩn hiện các icon tick error, đổi màu box edit text
     * Ngoài ra nếu Email hợp lệ sẽ gán email cho biến mBufEmail
     */
    private fun handleForEdittextEmail() {  // funtion xử lý cho edit text email

        edtEmail.addTextChangedListener {
            printLog("change text Email")
            val textEmail = edtEmail.text.toString() //Lấy chuỗi
            if (textEmail.isNotEmpty()) {           // Kiểm tra chuỗi có rống không
                imgTickEmail.visibility = View.VISIBLE  // không rỗng thì hiện icon
                this.mBufEmail =
                    if (textEmail.matches(regexEmail)) { // kiểm tra email hợp lệ bằng Regex
                        imgTickEmail.setImageResource(R.drawable.icon_tick)    // hợp lệ thì đổi tick xanh
                        edtEmail.setBackgroundResource(R.drawable.custom_edittext_tick)  // đổi màu xanh cho box
                        textEmail   // gán email cho biến mBufEmail
                    } else {
                        imgTickEmail.setImageResource(R.drawable.icon_error) // ko hợp set tick đỏ
                        edtEmail.setBackgroundResource(R.drawable.custom_edittext_error) //  đổi màu đỏ cho box
                        "" // gán rỗng cho mBufEmail
                    }
            } else {
                imgTickEmail.visibility = View.INVISIBLE  // trường hợp box rỗng thì ẩn tick
                edtEmail.setBackgroundResource(R.drawable.custom_select_edittext)// trả lại back ground cũ cho box
            }
        }

    }

    /**
     * hàm xử lý edit text Pass
     * hàm này kiểm tra: edit text có trống hay không
     *              - xem Mật khẩu nhập vào có hợp lệ không
     *              - đổi màu box edit text
     *              - điều khiển icon tick, error
     *              - gán mật khẩu cho biến mBufPass
     *            - ngoài ra nó con thực hiện nhiệm vụ của edit text Repass để điều khiển Edit text Repass
     */

    private fun handleForEdittextPass() {

        // 2 hàm bên dưới để xuất ra hướng dẫn nhập email ký kick vô box
        edtPass.setOnClickListener {
            resources.getString(R.string.text_rule_password).toast()
        }
        edtPass.setOnFocusChangeListener { view, b ->
            if (b) {
                resources.getString(R.string.text_rule_password).toast()
            }
        }

        // hàm bắt sự kiện thay đổi text. hàm này thực hiện công việc khá giống hàm trên,
        // nó chỉ khác biến và khác view
        // ngoài ra hàm thực hiện luôn công việc của Repass để kiểm tra lại sự khác nhau khi thay đổi text
        edtPass.addTextChangedListener {
            printLog("change text Pass")
            val textPass = edtPass.text.toString()
            if (textPass.isNotEmpty()) {
                imgTickPass.visibility = View.VISIBLE
                this.mBufPass = if (textPass.matches(regexPass)) {
                    imgTickPass.setImageResource(R.drawable.icon_tick)
                    edtPass.setBackgroundResource(R.drawable.custom_edittext_tick)
                    textPass
                } else {
                    imgTickPass.setImageResource(R.drawable.icon_error)
                    edtPass.setBackgroundResource(R.drawable.custom_edittext_error)
                    ""
                }
            } else {
                imgTickPass.visibility = View.INVISIBLE
                edtPass.setBackgroundResource(R.drawable.custom_select_edittext)
            }

            val textRepass = edtRePass.text.toString()
            if (textRepass.isNotEmpty()) {
                imgTickRepass.visibility = View.VISIBLE
                this.mFlatRepass = if (textRepass == mBufPass) {
                    imgTickRepass.setImageResource(R.drawable.icon_tick)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_tick)
                    true
                } else {
                    imgTickRepass.setImageResource(R.drawable.icon_error)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_error)
                    false
                }
            } else {
                imgTickRepass.visibility = View.INVISIBLE
                edtRePass.setBackgroundResource(R.drawable.custom_select_edittext)
            }
        }

    }

    /**
     * hàm xử lý Edit text Repass
     * Hàm này thực hiện:
     *          - kiểm tra Edit text có trống hay không
     *          - kiểm tra xem mật khẩu đã nhập có khớp với mật khẩu nhập lại hay không
     *          - đổi màu box và điều khiển icon tick, error
     */
    // hàm nay tương tự chỉ khác biến và khác view và khác sự kiện.
    // nó so sánh giữa text và mBufPass đã được lưu chữ ko so sánh regex
    private fun handleForEdittextRePass() {
        edtRePass.addTextChangedListener {
            printLog("change text Repass")
            val textRepass = edtRePass.text.toString()
            if (textRepass.isNotEmpty()) {
                imgTickRepass.visibility = View.VISIBLE
                this.mFlatRepass = if (textRepass == mBufPass) {
                    imgTickRepass.setImageResource(R.drawable.icon_tick)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_tick)
                    true
                } else {
                    imgTickRepass.setImageResource(R.drawable.icon_error)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_error)
                    false
                }
            } else {
                imgTickRepass.visibility = View.INVISIBLE
                edtRePass.setBackgroundResource(R.drawable.custom_select_edittext)
            }
        }

    }

    /**
     * hàm xử lý toast, xuất toast lên màn hình
     */
    private fun Any.toast(
        context: Context = this@MainActivity,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        mToast?.cancel()
        mToast = Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    /**
     * Log funtion
     */
    private fun printLog(st: String) {
        Log.d("AAA", st)
    }
}
