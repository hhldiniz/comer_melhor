package com.hugo.comermelhor.ui.util

import android.content.Context
import android.content.Intent

object ContentSharer {
    private val sendIntent = Intent(Intent.ACTION_SEND)

    fun shareText(context: Context, text: String, title: String? = null) {
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, title)
        context.startActivity(shareIntent)
    }
}