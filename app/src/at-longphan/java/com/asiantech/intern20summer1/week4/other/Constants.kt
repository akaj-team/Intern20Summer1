package com.asiantech.intern20summer1.week4.other


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

enum class RequestCode(val code: Int) {
    STORAGE_REQUEST(1),
    PICK_IMAGE_REQUEST(2),
    OPEN_CAMERA_REQUEST(3)
}

val PASSWORD_REGEX = "^(?=.*[0-9]).{8,16}\$".toRegex()

val MOBILE_NUMBER_REGEX = "^[0-9]{10}$".toRegex()