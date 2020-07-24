package com.asiantech.intern20summer1.week4.other

internal object RequestCode {
    const val STORAGE_REQUEST = 1
    const val PICK_IMAGE_REQUEST = 2
    const val OPEN_CAMERA_REQUEST = 3
}

internal object SignInActivityData {
    const val SIGN_IN_USER = "userLogin"
}

internal object IntentTitle {
    const val PICK_IMAGE_TITLE = "Pick image"
    const val OPEN_CAMERA_TITLE = "Open camera"
}

internal object IntentType {
    const val IMAGE = "image/*"
}

internal object CustomRegex {
    const val PASSWORD_REGEX = "^(?=.*[0-9]).{8,16}\$"
    const val MOBILE_NUMBER_REGEX = "^[0-9]{10}$"
}
