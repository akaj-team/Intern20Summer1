package com.asiantech.intern20summer1.week9

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.asiantech.intern20summer1.R

class MusicNotification(private val context: Context) {
    private var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        internal const val PAUSE = "pause"
        internal const val NEXT = "next"
        internal const val PREVIOUS = "previous"
        internal const val KILL = "kill"
        private const val REQUEST_CODE = 1
        internal const val CHANNEL_ID = "channelId"
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun createNotification(song: Song, isPlaying: Boolean): Notification? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val intent = Intent(context, NotificationActionService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(song.name)
            .setContentText(song.author)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .addAction(notificationAction(PREVIOUS, isPlaying))
            .addAction(notificationAction(PAUSE, isPlaying))
            .addAction(notificationAction(NEXT, isPlaying))
            .addAction(notificationAction(KILL, isPlaying))
            .setPriority(NotificationCompat.PRIORITY_LOW)
        return notification.build()
    }

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action {
        val icon: Int = when (action) {
            PREVIOUS -> R.drawable.previous
            PAUSE -> if (isPlaying) {
                R.drawable.play
            } else {
                R.drawable.pause
            }
            NEXT -> R.drawable.next
            KILL -> R.drawable.back
            else -> R.drawable.previous
        }
        return NotificationCompat.Action.Builder(icon, action, intentAction(action)).build()
    }

    private fun intentAction(action: String): PendingIntent {
        val broadcastIntent = Intent()
        broadcastIntent.action = action
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(CHANNEL_ID, "Service Channel", IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(serviceChannel)
    }
}