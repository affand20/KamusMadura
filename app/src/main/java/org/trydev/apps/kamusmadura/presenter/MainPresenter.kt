package org.trydev.apps.kamusmadura.presenter

import android.content.Context
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import org.trydev.apps.kamusmadura.db.database
import org.trydev.apps.kamusmadura.model.Kosakata
import org.trydev.apps.kamusmadura.view.MainView

class MainPresenter(private val view:MainView, private val context: Context):AnkoLogger {
    fun getAllIndonesia(){
        doAsync {
            val data = context.database.use {
                select(Kosakata.TABLE_KOSAKATA).orderBy(Kosakata.indonesia, SqlOrderDirection.ASC).parseList(classParser<Kosakata>())
            }
            uiThread {
                info("MAIN PRESENTER INDONESIA ${data.size}")
                data.let {
                    info("MAIN PRESENTER INDONESIA $data")
                }
                view.showListKosakata(data)
            }
        }
    }

    fun getAllMadura(){
        doAsync {
            val data = context.database.use {
                select(Kosakata.TABLE_KOSAKATA).orderBy(Kosakata.madura, SqlOrderDirection.ASC).parseList(classParser<Kosakata>())
            }
            uiThread {
                info("MAIN PRESENTER MADURA ${data.size}")
                data.let {
                    info("MAIN PRESENTER MADURA $data")
                }
                view.showListKosakata(data)
            }

        }
    }
}