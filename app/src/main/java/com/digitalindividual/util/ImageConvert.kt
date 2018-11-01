package com.digitalindividual.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

class ImageConvert {

    companion object {

        fun turnToBytes(bitmap: Bitmap?): ByteArray{

            var stream: ByteArrayOutputStream = ByteArrayOutputStream()

            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)

            return stream.toByteArray()

        }

        fun turnToBitmap(img: ByteArray): Bitmap {

            return BitmapFactory.decodeByteArray(img, 0 , img.size)

        }

        fun turnToBitmap(drawable: Drawable): Bitmap{

            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)

            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            return bitmap

        }

    }

}