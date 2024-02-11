package org.kuittaan.voicediary.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun saveImageToPrivateStorage(context: Context, filename: String, fileContents: Bitmap) {

    val file = File(context.filesDir, filename)

    // Create an output stream using the file
    FileOutputStream(file).use { fileOutputStream ->
        // Compress the bitmap to the output stream
        fileContents.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    }
}