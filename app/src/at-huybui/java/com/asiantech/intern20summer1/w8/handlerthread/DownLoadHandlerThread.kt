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

    fun sendOrder(time: Int) {
        val message = Message()
        message.obj = time
        handler?.sendMessage(message)
    }
    private fun getHandler(looper: Looper): Handler {
        return object : Handler(looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val foodOrder = msg.obj as Int
                val processedMessage = Message()
                processedMessage.obj = foodOrder
                uiHandler.sendMessage(processedMessage)
            }

        }
    }

}
