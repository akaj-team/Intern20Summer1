package com.asiantech.intern20summer1.week9.extensions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.models.Song
import com.asiantech.intern20summer1.week9.views.MusicMediaActivity

class CreateNotification(private val context: Context) {
    private var session: MediaSessionCompat? = null
    private var manager: NotificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        private const val ACTION_PAUSE = "pause"
        private const val ACTION_SKIP_NEXT = "next"
        private const val ACTION_PREVIOUS = "previous"
        private const val ACTION_KILL_MEDIA = "kill"
        private const val REQUEST_CODE = 100
        private const val CHANNEL_ID = "channelId"
        private const val SHOW_ACTION_THIRD_KEY = 3
    }

    fun createNotificationMusic(song: Song, isPlaying: Boolean): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MusicMediaActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT
        )
        //create
        notificationBuilder.apply {
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(session?.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2, SHOW_ACTION_THIRD_KEY)
            )
            setContentTitle(song.songName)
            setSmallIcon(R.drawable.logo)
            setLargeIcon(Utils.convertToBitmap(context, Uri.parse(song.imgUri)))
            setContentText(song.artist)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            setOngoing(true)
            addAction(notificationAction(ACTION_PREVIOUS, isPlaying))
            addAction(notificationAction(ACTION_PAUSE, isPlaying))
            addAction(notificationAction(ACTION_SKIP_NEXT, isPlaying))
            addAction(notificationAction(ACTION_KILL_MEDIA, isPlaying))
        }
        return notificationBuilder.build()

    }

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action? {
        val icon: Int = when (action) {
            ACTION_PREVIOUS -> R.drawable.ic_baseline_skip_previous_24
            ACTION_PAUSE -> if (isPlaying) {
                R.drawable.ic_baseline_play_circle_filled_24
            } else {
                R.drawable.ic_baseline_pause_circle_filled_24
            }
            ACTION_SKIP_NEXT -> R.drawable.ic_baseline_skip_next_24
            ACTION_KILL_MEDIA -> R.drawable.ic_baseline_close_24
            else -> R.drawable.ic_baseline_skip_next_24
        }
        return NotificationCompat.Action.Builder(icon, action, intentAction(action)).build()
    }

    private fun createNotificationChannel() {
        if (SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, context.getString(R.string.create_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun intentAction(action: String): PendingIntent {
        val broadCastIntent = Intent()
        broadCastIntent.action = action
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            broadCastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
