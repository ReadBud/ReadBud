package com.binayshaw7777.readbud.utils

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun extractTextFromBitmap(bitmap: Bitmap): String {
    return withContext(Dispatchers.Default) {
        runCatching {
            val image = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val result = recognizer.process(image).await()

            val extractedText = StringBuilder()
            for (block in result.textBlocks) {
                for (line in block.lines) {
                    for (element in line.elements) {
                        extractedText.append(element.text).append(" ")
                    }
                    extractedText.append("\n")
                }
                extractedText.append("\n")
            }
            extractedText.toString()
        }.getOrElse {
            it.printStackTrace()
            ""
        }
    }
}

suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result!!)
        } else {
            continuation.resumeWithException(task.exception!!)
        }

    }
}