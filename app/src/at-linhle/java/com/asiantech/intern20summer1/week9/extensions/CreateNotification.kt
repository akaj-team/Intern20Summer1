package com.asiantech.intern20summer1.week9.extensions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week9.models.Song
import com.asiantech.intern20summer1.week9.services.MusicService
import com.asiantech.intern20summer1.week9.views.MusicMediaActivity

class CreateNotification(musicService: MusicService) {

    companion object {
        internal const val ACTION_PAUSE = "pause"
        internal const val ACTION_SKIP_NEXT = "next"
        internal const val ACTION_PREVIOUS = "previous"
        internal const val ACTION_KILL_MEDIA = "kill"
        private const val REQUEST_CODE = 100
        private const val CHANNEL_ID = "channelId"
    }

    private var builder: NotificationCompat.Builder? = null
    private var context: Context = musicService.baseContext
    private var session: MediaSessionCompat? = null
    private var manager: NotificationManager = musicService
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotification(song: Song, isPlaying: Boolean): Notification? {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MusicMediaActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder?.apply {
            setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(session?.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2, 3)
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
        return builder?.build()
    }

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action {
        val icon: Int = when (action) {
            ACTION_PREVIOUS -> R.drawable.ic_baseline_skip_previous_24
            ACTION_PAUSE -> if (isPlaying) {
                R.drawable.ic_baseline_play_circle_filled_24
            } else {
                R.drawable.ic_baseline_pause_circle_filled_24
            }
            ACTION_SKIP_NEXT -> R.drawable.ic_baseline_skip_next_24
            ACTION_KILL_MEDIA -> R.drawable.ic_baseline_close_24
            else -> R.drawable.ic_baseline_skip_previous_24
        }
        return NotificationCompat.Action.Builder(
            icon,
            action,
            intentAction(action)
        ).build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
