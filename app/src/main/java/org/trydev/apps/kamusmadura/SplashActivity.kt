package org.trydev.apps.kamusmadura

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.trydev.apps.kamusmadura.view.MainActivity

class SplashActivity: AppCompatActivity() {

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mp = MediaPlayer.create(this, R.raw.audiokamusmadura)
        //playSound()
        startActivity<MainActivity>()


        fun playSound(){
            if (mp.isPlaying){
                mp.pause()
            }
            mp.seekTo(0)
            mp.start()
        }

        playSound()
        finish()
    }
}