package com.asiantech.intern20summer1.week4.other

internal object RequestCode {
    const val PICK_IMAGE_REQUEST = 1
    const val OPEN_CAMERA_REQUEST = 2
}

internal object SignInActivityData {
    const val SIGN_IN_USER = "userLogin"
}

internal object IntentType {
    const val IMAGE = "image/*"
}

internal object CustomRegex {
    const val PASSWORD_REGEX = "^(?=.*[0-9]).{8,16}\$"
    const val MOBILE_NUMBER_REGEX = "^[0-9]{10}$"
}
