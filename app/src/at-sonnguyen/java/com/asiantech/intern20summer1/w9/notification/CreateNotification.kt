package com.asiantech.intern20summer1.w9.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.ForegroundService
import com.asiantech.intern20summer1.w9.ForegroundService.Companion.CHANNEL_ID
import com.asiantech.intern20summer1.w9.activity.MusicPlayerActivity
import com.asiantech.intern20summer1.w9.data.MusicAction
import com.asiantech.intern20summer1.w9.data.Song
import com.asiantech.intern20summer1.w9.data.SongData

class CreateNotification(foregroundService: ForegroundService) {
    companion object {
        private const val REQUEST_CODE = 102
    }

    private var builder: NotificationCompat.Builder? = null
    private var context: Context = foregroundService.baseContext
    private var session: MediaSessionCompat? = null
    private var manager: NotificationManager = foregroundService
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotification(song: Song, isPlaying: Boolean): Notification? {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MusicPlayerActivity::class.java)
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
            setSmallIcon(R.drawable.ic_music)
            setLargeIcon(SongData.convertUriToBitmap(song.uri, context))
            setContentText(song.singerName)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            setOngoing(true)
            addAction(notificationAction(MusicAction.PREVIOUS, isPlaying))
            addAction(notificationAction(MusicAction.PAUSE, isPlaying))
            addAction(notificationAction(MusicAction.NEXT, isPlaying))
            addAction(notificationAction(MusicAction.CLOSE, isPlaying))
        }
        return builder?.build()
    }

    private fun notificationAction(
        action: String,
        isPlaying: Boolean
    ): NotificationCompat.Action {
        val icon: Int = when (action) {
            MusicAction.PREVIOUS -> R.drawable.ic_baseline_skip_previous_24
            MusicAction.PAUSE -> if (isPlaying) {
                R.drawable.ic_baseline_pause_24
            } else {
                R.drawable.ic_baseline_play_arrow_24
            }
            MusicAction.NEXT -> R.drawable.ic_baseline_skip_next_24
            MusicAction.CLOSE -> R.drawable.ic_baseline_close_24
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
                CHANNEL_ID, context.getString(R.string.w9_channel_name),
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
