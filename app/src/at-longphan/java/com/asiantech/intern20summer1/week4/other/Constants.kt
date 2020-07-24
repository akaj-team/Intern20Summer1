package com.asiantech.intern20summer1.week4.other

object RequestCode {
    internal const val STORAGE_REQUEST = 1
    internal const val PICK_IMAGE_REQUEST = 2
    internal const val OPEN_CAMERA_REQUEST = 3
}

enum class SignInActivityData(val data: String) {
    SIGN_IN_USER("userLogin")
}

enum class IntentTitle(val string: String) {
    PICK_IMAGE_TITLE("Pick image"),
    OPEN_CAMERA_TITLE("Open camera")
}

enum class IntentType(val string: String) {
    IMAGE("image/*")
}

const val PASSWORD_REGEX = "^(?=.*[0-9]).{8,16}\$"

const val MOBILE_NUMBER_REGEX = "^[0-9]{10}$"
