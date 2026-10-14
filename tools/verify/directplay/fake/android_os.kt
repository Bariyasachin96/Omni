package android.os

// SpeechOut only hands this to AudioTrack.setPlaybackPositionUpdateListener;
// the fake AudioTrack delivers its events on the clock itself.
open class Handler(@Suppress("UNUSED_PARAMETER") looper: Any? = null)
