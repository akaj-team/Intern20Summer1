package com.asiantech.intern20summer1.w8.handlerthread

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.asiantech.intern20summer1.w8.DownLoadActivity

class DownLoadHandlerThread(private var uiHandler: DownLoadActivity.UiHandler) :
    HandlerThread("DownLoadHandlerThread") {

    private var handler: Handler? = null

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = getHandler(looper)
    }

    fun sendOrder(progress: Int) {
        val message = Message()
        message.obj = progress
        handler?.sendMessage(message)
    }

    private fun getHandler(looper: Looper): Handler {
        return object : Handler(looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val processedMessage = Message()
                processedMessage.obj =  msg.obj as Int
                uiHandler.sendMessage(processedMessage)
            }
        }
    }
}
