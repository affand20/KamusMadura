package org.trydev.apps.kamusmadura.view

import org.trydev.apps.kamusmadura.model.Kosakata

interface MainView {
    fun showListKosakata(data:List<Kosakata>)
}