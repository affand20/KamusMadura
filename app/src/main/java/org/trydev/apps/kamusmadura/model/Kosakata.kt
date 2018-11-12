package org.trydev.apps.kamusmadura.model

data class Kosakata(
    val indonesia:String,
    val madura:String
)
{
    companion object {
        const val TABLE_KOSAKATA = "TABLE_KOSAKATA"
        const val indonesia = "INDONESIA"
        const val madura = "MADURA"
    }
}