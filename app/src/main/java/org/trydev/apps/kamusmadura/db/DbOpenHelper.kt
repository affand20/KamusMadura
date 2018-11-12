package org.trydev.apps.kamusmadura.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import org.trydev.apps.kamusmadura.model.Kosakata

class DbOpenHelper (ctx:Context):ManagedSQLiteOpenHelper(ctx,"Kamus.db", null, 1) {
    companion object {
        private var instance:DbOpenHelper? = null

        @Synchronized
        fun getInstance(ctx:Context):DbOpenHelper{
            if (instance==null){
                instance = DbOpenHelper(ctx.applicationContext)
            }
            return instance as DbOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Kosakata.TABLE_KOSAKATA, true,
            Kosakata.indonesia to TEXT,
            Kosakata.madura to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable(Kosakata.TABLE_KOSAKATA, true)
    }
}

val Context.database:DbOpenHelper
get() = DbOpenHelper.getInstance(applicationContext)