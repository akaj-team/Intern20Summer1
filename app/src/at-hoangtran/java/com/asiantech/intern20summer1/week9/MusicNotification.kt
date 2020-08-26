package com.asiantech.intern20summer1.week9

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.asiantech.intern20summer1.R

class MusicNotification(private val context: Context) {
    private var session: MediaSession? = null
    private var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        private const val PAUSE = "pause"
        private const val PLAY = "play"
        private const val NEXT = "next"
        private const val PREVIOUS = "previous"
        private const val KILL = "kill"
        private const val REQUEST_CODE = 1
        internal const val CHANNEL_ID = "channelId"
        private const val THIRD_KEY = 3
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun createNotification(song: Song, context: Context, playButton: Int, pos: Int, size: Int) {
        val notificationCompat = NotificationManagerCompat.from(context)
        val mediaSessionCompat = MediaSession(context, "tag")

        val intentPrevious = Intent(context, NotificationActionService::class.java)
            .setAction(PREVIOUS)
        val pendingIntentPrevious = PendingIntent.getBroadcast(
            context,
            0,
            intentPrevious,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val previous = R.drawable.previous

        val intentPlay = Intent(context, NotificationActionService::class.java)
            .setAction(PLAY)
        val pendingIntentPlay = PendingIntent.getBroadcast(
            context,
            0,
            intentPlay,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val play = R.drawable.play

        val intentNext = Intent(context, NotificationActionService::class.java)
            .setAction(PLAY)
        val pendingIntentNext = PendingIntent.getBroadcast(
            context,
            0,
            intentNext,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val next = R.drawable.next

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(song.name)
            .setContentText(song.author)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .addAction(previous, "Previous", pendingIntentPrevious)
            .addAction(play, "Play", pendingIntentPlay)
            .addAction(next, "Next", pendingIntentNext)
            .setStyle(androidx.media)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        notificationCompat.notify(1, notification)
    }
}