package org.trydev.apps.kamusmadura.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.OkHttpClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.textChangedListener
import org.jetbrains.anko.toast
import org.trydev.apps.kamusmadura.R
import org.trydev.apps.kamusmadura.adapter.MainAdapter
import org.trydev.apps.kamusmadura.db.database
import org.trydev.apps.kamusmadura.model.Kosakata
import org.trydev.apps.kamusmadura.pref.Preference
import org.trydev.apps.kamusmadura.presenter.MainPresenter


class MainActivity : AppCompatActivity(), MainView, AnkoLogger {

    private var SWITHCER:String = "INDONESIA_TO_MADURA"
    private lateinit var presenter:MainPresenter
    private lateinit var adapter:MainAdapter
    private var listKategori:MutableList<Kosakata> = mutableListOf()
    private var listKategoriCopy:MutableList<Kosakata> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)
        OkHttpClient().newBuilder().addNetworkInterceptor(StethoInterceptor()).build()

///////////////  INSERT DATA JUST THE FIRST TIME  ////////////
        val pref = Preference(this)
        info("PREFS = ${pref.firstRun}")
        if (pref.firstRun){
            insertKosakata()
            pref.firstRun = false
        }

//////////////  SWITCH LANGUAGE  ////////////////////////////
        switch_translate.onClick {
            var temp = translate_from.text.toString()
            translate_from.text = translate_to.text.toString()
            translate_to.text = temp
            when(SWITHCER){
                "INDONESIA_TO_MADURA" ->{
                    SWITHCER = "MADURA_TO_INDONESIA"
                    input_kata.hint = "Terjemahkan Madura ke Indonesia"
                    presenter.getAllMadura()
                }
                "MADURA_TO_INDONESIA" ->{
                    SWITHCER = "INDONESIA_TO_MADURA"
                    input_kata.hint = "Terjemahkan Indonesia ke Madura"
                    presenter.getAllIndonesia()
                }
            }

            info("SWITCHER = $SWITHCER")
        }

        presenter = MainPresenter(this, this)
        presenter.getAllIndonesia()

        rv_kosakata.layoutManager = LinearLayoutManager(this)


//        insertKosakata()

/////////////  REAL TIME CHANGE RECYCLERVIEW ITEM //////////////////
        input_kata.textChangedListener {
            afterTextChanged {
                when (SWITHCER){
                    "INDONESIA_TO_MADURA" ->{
                        val newList = listKategoriCopy.filter { kata -> kata.indonesia.contains(input_kata.text.toString()) }
                        newList.let{
                            info("REALTIME CHANGE = $it")
                            listKategori.clear()
                            listKategori.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    "MADURA_TO_INDONESIA" ->{
                        val newList = listKategoriCopy.filter { kata -> kata.madura.contains(input_kata.text.toString()) }
                        newList.let{
                            info("REALTIME CHANGE = $it")
                            listKategori.clear()
                            listKategori.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

            }
        }
    }

    private fun insertKosakata(){
        applicationContext.assets.open("mobtekkamusmadura1.csv").bufferedReader().use{
            val kosakata = it.lineSequence().iterator()
            while (kosakata.hasNext()){
                val line = kosakata.next()
                val data = line.split(",")
                database.use {
                    insert(Kosakata.TABLE_KOSAKATA,
                Kosakata.indonesia to data[1],
                Kosakata.madura to data[2])
        }
            }
        }
    }
//
//    val kosakata = listOf(Kosakata("akar","ramok"),Kosakata("ampun","saporah"),Kosakata("anyam","ngekak"),Kosakata("asap","kokos"),Kosakata("awan","ondem"),Kosakata("terimakasih","sakalangkong"))
//
//    for (i in kosakata.indices){
//        database.use {
//            insert(Kosakata.TABLE_KOSAKATA,
//                Kosakata.indonesia to kosakata[i].indonesia,
//                Kosakata.madura to kosakata[i].madura)
//        }
//    }
//    toast("Persiapan telah selesai...")

    override fun showListKosakata(data: List<Kosakata>) {
        data.let {
            info("MAIN ACTIVITY $it}")
            listKategori.clear()
            listKategori.addAll(it)
            listKategoriCopy.clear()
            listKategoriCopy.addAll(it)
            adapter = MainAdapter(listKategori, SWITHCER)
            rv_kosakata.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}
