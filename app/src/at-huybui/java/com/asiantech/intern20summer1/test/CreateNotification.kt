package com.asiantech.intern20summer1.test

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.models.Song

class CreateNotification {
    companion object {
        const val CHANNEL_ID = "channel_1"
        const val ACTION_PREVIUOS = "previ"
        const val ACTION_PLAY = "play"
        const val ACTION_NEXT = "pause"
        var notification = Notification()
    }

    fun createNotification(context: Context, song: Song, playerButton: Int, pos: Int, size: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            var notificationManagerCompat = NotificationManagerCompat.from(context)
            var icon = song.getPicture(context,false)
            if(icon == null){
                icon = context.getDrawable(R.drawable.img_logo_music)?.toBitmap()
            }
            var mediaSessionCompat = MediaSessionCompat(context, "tag")

            var pendingIntentPrevius: PendingIntent? = null
            var drv_previus: Int = 0
            if (pos == 0) {
                pendingIntentPrevius = null
                drv_previus = 0
            } else {
                var intentPrevious =
                    Intent(context, NotificationService::class.java).setAction(ACTION_PREVIUOS)
                pendingIntentPrevius = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentPrevious,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                drv_previus = R.drawable.ic_previous_notification
            }

            var intentPlay =
                Intent(context, NotificationService::class.java).setAction(ACTION_PLAY)
            var pendingIntentPlay = PendingIntent.getBroadcast(
                context,
                0,
                intentPlay,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            var pendingIntentNext: PendingIntent? = null
            var drv_next: Int = 0
            if (pos == size) {
                pendingIntentNext = null
                drv_next = 0
            } else {
                var intentNext =
                    Intent(
                        context,
                        NotificationService::class.java
                    ).setAction(ACTION_NEXT)
                pendingIntentNext = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentNext,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                drv_next = R.drawable.ic_next_notification
            }



            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play_button)
                .setLargeIcon(icon)
                .setContentTitle(song.nameSong)
                .setContentText(song.singer)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(drv_previus, "previous", pendingIntentPrevius)
                .addAction(playerButton, "play", pendingIntentPlay)
                .addAction(drv_next, "next", pendingIntentNext)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken)
                )
                .setPriority(NotificationCompat.PRIORITY_LOW).build()
            notification.flags = Notification.FLAG_NO_CLEAR
            notificationManagerCompat.notify(1, notification)
        }
    }
}