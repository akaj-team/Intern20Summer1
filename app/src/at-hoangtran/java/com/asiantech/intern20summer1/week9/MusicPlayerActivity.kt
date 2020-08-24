package com.asiantech.intern20summer1.week9

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_music_player.*

class MusicPlayerActivity : AppCompatActivity() {

    private var totalTime: Int = 0
    private lateinit var mp: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        handleButton()
//        handleProgressBar()
    }


    private fun handleButton() {
        val intent = Intent(this, MusicService::class.java)
        btnSongPlayerPlayPause.setOnClickListener {
            if (mp.isPlaying) {
                stopService(intent)
                btnSongPlayerPlayPause.setBackgroundResource(R.drawable.play)
            } else {
                startService(intent)
                btnSongPlayerPlayPause.setBackgroundResource(R.drawable.pause)
            }
        }
    }

    private fun handleProgressBar() {
        seekBarSongPlayer.max = totalTime
        seekBarSongPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun handleThread() {
        Thread(Runnable {
            while (true) {
                try {
                    val msg = Message()
                    msg.what = mp.currentPosition
//                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()

        var handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val currentPosition = msg.what
                seekBarSongPlayer.progress = currentPosition
                val elapsedTime = createTimeLabel(currentPosition)
                tvElapsedTime.text = elapsedTime
                val remainingTime = createTimeLabel(totalTime - currentPosition)
                tvRemainingTime.text = "-$remainingTime"
            }
        }
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel: String
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }
}
