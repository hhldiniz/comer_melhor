package com.hugo.comermelhor.ui.util.extensions
import android.content.Context
import android.net.Uri

fun Uri.takePermission(context: Context, flags: Int): Result<Boolean> {
    val contentResolver = context.contentResolver

    // Take persistable URI permissions
    try {
        contentResolver.takePersistableUriPermission(this, flags)
        return Result.success(true)
    } catch (e: SecurityException) {
        return Result.failure(e)
    }
}