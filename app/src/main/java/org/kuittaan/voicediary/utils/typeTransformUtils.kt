package org.kuittaan.voicediary.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileDescriptor
import java.io.IOException

fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
    try {
        lateinit var contentResolver: ContentResolver
        val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}