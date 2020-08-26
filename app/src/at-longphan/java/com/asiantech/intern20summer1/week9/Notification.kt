package com.asiantech.intern20summer1.week9

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
import com.asiantech.intern20summer1.week9.model.Song
import com.asiantech.intern20summer1.week9.model.Units
import com.asiantech.intern20summer1.week9.model.Units.ACTION_KILL_MEDIA
import com.asiantech.intern20summer1.week9.model.Units.ACTION_PLAY_PAUSE
import com.asiantech.intern20summer1.week9.model.Units.ACTION_PREVIOUS
import com.asiantech.intern20summer1.week9.model.Units.ACTION_SKIP_NEXT
import com.asiantech.intern20summer1.week9.service.PlayMusicService

class Notification(playMusicService: PlayMusicService) {

    companion object {
        private const val REQUEST_CODE = 101
        private const val NOTIFICATION_CHANNEL_ID = "CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "Foreground Service Channel"
    }

    private var builder: NotificationCompat.Builder? = null
    private var context: Context = playMusicService.baseContext
    private var session: MediaSessionCompat? = null
    private var manager: NotificationManager? = playMusicService
        .getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

    fun createNotification(music: Song, isPlaying: Boolean): Notification? {
        builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        createNotificationChannel()
        val intentActivity = Intent(context, MainActivityWeek9::class.java)
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
            setContentTitle(music.name)
            setSmallIcon(R.drawable.ic_music)
            setLargeIcon(Units.songArt(Uri.parse(music.path), context))
            setContentText(music.artist)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            setOngoing(true)
            addAction(notificationAction(ACTION_PREVIOUS, isPlaying))
            addAction(notificationAction(ACTION_PLAY_PAUSE, isPlaying))
            addAction(notificationAction(ACTION_SKIP_NEXT, isPlaying))
            addAction(notificationAction(ACTION_KILL_MEDIA, isPlaying))
        }
        return builder?.build()
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

    private fun notificationAction(action: String, isPlaying: Boolean): NotificationCompat.Action {
        val icon: Int = when (action) {
            ACTION_PREVIOUS -> R.drawable.ic_previous
            ACTION_PLAY_PAUSE -> if (isPlaying) {
                R.drawable.ic_play
            } else {
                R.drawable.ic_pause
            }
            ACTION_SKIP_NEXT -> R.drawable.ic_next
            ACTION_KILL_MEDIA -> R.drawable.ic_close_black_24
            else -> R.drawable.ic_previous
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
                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager?.createNotificationChannel(serviceChannel)
        }
    }
}
