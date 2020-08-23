package com.asiantech.intern20summer1.week9.controller

import android.content.Context
import android.widget.MediaController

class MusicController : MediaController {

    constructor(c: Context) : super(c) {

    }

    override fun hide() {}
}