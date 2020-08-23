package com.asiantech.intern20summer1.w9.fragments

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w9.activitys.MusicActivity
import com.asiantech.intern20summer1.w9.managers.SongRecyclerAdapter
import com.asiantech.intern20summer1.w9.models.Song
import com.asiantech.intern20summer1.w9.services.BackgroundSoundService
import com.asiantech.intern20summer1.w9.services.BackgroundSoundService.LocalBinder
import kotlinx.android.synthetic.`at-huybui`.w9_fragment_music.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class PlayerFragment : Fragment() {

    companion object {
        internal fun newInstance() = PlayerFragment()
    }

    private val songLists = mutableListOf<Song>()
    private val songAdapter = SongRecyclerAdapter(songLists)
    private var service = BackgroundSoundService()
    private var svc = Intent()
    var bound = false
    private var connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }

        override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?) {
            val binder = iBinder as LocalBinder
            service = binder.getService()
            initPlayerBar(service)
            bound = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStop() {
        super.onStop()
        (activity as MusicActivity).unbindService(connection)
        bound = false
    }


}
