package values

import android.content.Context
import android.widget.Toast


/**
 * This function will handle toast, it will used to show string to activity display
 */
private var mToast: Toast? = null
fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    mToast?.cancel()
    mToast = Toast.makeText(context, this.toString(), duration).apply { show() }
}
