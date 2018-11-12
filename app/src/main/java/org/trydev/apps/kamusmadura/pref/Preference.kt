package org.trydev.apps.kamusmadura.pref

import android.content.Context
import androidx.core.content.edit

class Preference(private val context: Context) {

    private val prefs = context.getSharedPreferences("KamusPref", Context.MODE_PRIVATE)

    var firstRun:Boolean = prefs.getBoolean("isFirstRun", true)
    set(value) = prefs.edit {
        putBoolean("isFirstRun",value)
    }


}