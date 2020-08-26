package com.asiantech.intern20summer1.w9.notification

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/25/20
 * This is interface for player click control
 */

interface ClickPlayable {
    fun onMusicPrevious()
    fun onMusicResume()
    fun onMusicPause()
    fun onMusicNext()
    fun onMusicStart()
}
