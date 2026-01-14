package kndroidx.demo.media

import android.support.v4.media.session.MediaSessionCompat
import kndroidx.media.MediaSessionService

class MusicSessionService: MediaSessionService() {
    override val smallIcon =  R.drawable.ic_launcher_foreground
    override val mediaSessionCallback = object : MediaSessionCompat.Callback(){}
    override val mediaChannelId = "music_notification"
    override val mediaChannelName = "music"
    override val mediaChannelDesc = "for music playing"
}