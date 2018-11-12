package org.trydev.apps.kamusmadura.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.find
import org.trydev.apps.kamusmadura.R
import org.trydev.apps.kamusmadura.model.Kosakata

class MainAdapter(private val kosakata: List<Kosakata>, private val switcher:String):RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.kosakata_layout, parent, false))
    }

    override fun getItemCount(): Int = kosakata.size

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bindItems(kosakata[position], switcher)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private val kata:TextView = view.find(R.id.kata)
        private val arti:TextView = view.find(R.id.arti)

        fun bindItems(item:Kosakata, switcher: String){
            when(switcher){
                "INDONESIA_TO_MADURA" ->{
                    kata.text = item.indonesia
                    arti.text = item.madura
                }
                "MADURA_TO_INDONESIA" ->{
                    kata.text = item.madura
                    arti.text = item.indonesia
                }
            }

        }
    }
}