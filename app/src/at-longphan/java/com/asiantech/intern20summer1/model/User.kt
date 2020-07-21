package com.asiantech.intern20summer1.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    var email: String? = null,
    var fullName: String? = null,
    var mobileNumber: String? = null,
    var password: String? = null,
    var avatar: String? = null
) : Parcelable {
    constructor(email: String?, password: String?) : this(){
        this.email = email
        this.password = password
    }
}
