package org.trydev.apps.kamusmadura

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.trydev.apps.kamusmadura.view.MainActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<MainActivity>()

        finish()
    }
}