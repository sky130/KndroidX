package kndroidx.demo.media

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kndroidx.media.MediaSessionService

class MainActivity : AppCompatActivity() {
    lateinit var service: MediaSessionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMusicService()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initMusicService(){
        ContextCompat.startForegroundService(this, Intent(this, MusicSessionService::class.java))
    }

//    private fun initMusicService() {
//        bindService(
//            serviceIntent, serviceConnection, BIND_AUTO_CREATE
//        )
//    }
//
//    private val serviceIntent by lazy { Intent(this, MusicSessionService::class.java) }
//    private val serviceConnection: ServiceConnection by lazy {
//        object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
//                if (binder is MediaSessionService.ServiceBinder) {
//                    service = binder.service
//                    service.updatePlaybackState(isPlaying = true, position = 1000)
//                }
//            }
//
//            override fun onServiceDisconnected(name: ComponentName) {
//
//            }
//        }
//    }
}