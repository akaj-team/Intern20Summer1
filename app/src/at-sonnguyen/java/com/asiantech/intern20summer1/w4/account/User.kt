package com.asiantech.intern20summer1.w4.account

import android.net.Uri
import java.io.Serializable

class User(var fullName: String, var email: String, var phoneNumber: String, var password: String  ) :
    Serializable {
    private var nameUser = fullName
    private var emailUser = email
    private var phoneNumberUser = phoneNumber
    private var passwordUser = password
//    private var uriUser = uri

}