package kndroidx.media

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

abstract class MediaSessionService : Service() {
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    @RequiresApi(Build.VERSION_CODES.N)
    open val mediaChannelImportance = NotificationManager.IMPORTANCE_LOW
    open val mediaSessionTag = "MusicService"
    open val mediaNotificationId = 1

    @get:DrawableRes
    abstract val smallIcon: Int
    abstract val mediaSessionCallback: MediaSessionCompat.Callback
    abstract val mediaChannelId: String
    abstract val mediaChannelName: String
    abstract val mediaChannelDesc: String
    val mediaSession: MediaSessionCompat by lazy {
        MediaSessionCompat(this, mediaSessionTag).apply {
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                        MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS or
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )
            setCallback(mediaSessionCallback)
            isActive = true
        }
    }

    open fun onCreateNotification(builder: NotificationCompat.Builder) = Unit

    fun sendNotification() {
        createNotificationChannel()
        notificationManager.notify(mediaNotificationId, createNotification())
    }

    fun updateMetaData(
        title: String? = null,
        artist: String? = null,
        album: String? = null,
        duration: Long? = null,
        numTracks: Long? = null,
        cover: Bitmap? = null,
        block: MediaMetadataCompat.Builder.() -> Unit = {}
    ) {
        MediaMetadataCompat.Builder().apply {
            title?.let { putString(MediaMetadataCompat.METADATA_KEY_TITLE, it) }
            artist?.let { putString(MediaMetadataCompat.METADATA_KEY_ARTIST, it) }
            album?.let { putString(MediaMetadataCompat.METADATA_KEY_ALBUM, it) }
            duration?.let { putLong(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, it) }
            numTracks?.let { putLong(MediaMetadataCompat.METADATA_KEY_DURATION, it) }
            cover?.let { putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, it) }
            block()
        }.build().let { mediaSession.setMetadata(it) }
    }

    fun updatePlaybackState(
        isPlaying: Boolean = false,
        position: Long = 0L,
        playbackSpeed: Float = 1.0f
    ) {
        val playbackState = PlaybackStateCompat.Builder()
            .setActions(MEDIA_SESSION_ACTIONS)
            .setState(
                if (isPlaying) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED,
                position,
                playbackSpeed
            ).build()
        mediaSession.setPlaybackState(playbackState)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(mediaChannelId, mediaChannelName, mediaChannelImportance).apply {
                description = mediaChannelDesc
                enableLights(false)
                enableVibration(false)
                notificationManager.createNotificationChannel(this)
            }
        }
    }

    private fun createNotification() =
        NotificationCompat.Builder(this, mediaChannelId).apply {
            setSmallIcon(smallIcon)
            setStyle(
                androidx.media.app.NotificationCompat
                    .MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            onCreateNotification(this)
        }.build()

    inner class ServiceBinder : Binder() {
        val service = this@MediaSessionService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return ServiceBinder()
    }

    override fun onDestroy() {
        mediaSession.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendNotification()
        updatePlaybackState(isPlaying = true, position = 1000)
        return START_NOT_STICKY
    }

    companion object {
        const val MEDIA_SESSION_ACTIONS = (PlaybackStateCompat.ACTION_PLAY
                or PlaybackStateCompat.ACTION_PAUSE
                or PlaybackStateCompat.ACTION_PLAY_PAUSE
                or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                or PlaybackStateCompat.ACTION_STOP
                or PlaybackStateCompat.ACTION_SEEK_TO)
    }
}