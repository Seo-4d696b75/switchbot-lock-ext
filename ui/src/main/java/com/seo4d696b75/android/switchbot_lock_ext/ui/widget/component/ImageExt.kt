package com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.unit.ColorProvider

@Composable
fun ImageProvider(
    @DrawableRes id: Int,
    tintColor: ColorProvider? = null,
): ImageProvider {
    val context = LocalContext.current
    return tintColor?.let {
        val color = it.getColor(context)
        val paint = Paint().apply {
            colorFilter = PorterDuffColorFilter(
                color.toArgb(),
                PorterDuff.Mode.SRC_IN,
            )
        }
        val drawable = requireNotNull(context.getDrawable(id))
        val src = drawable.toBitmap(256,256)
        val dst = Bitmap.createBitmap(
            src.width,
            src.height,
            Bitmap.Config.ARGB_8888,
        ).apply {
            Canvas(this).apply {
                drawBitmap(src, 0f, 0f, paint)
            }
        }
        ImageProvider(dst)
    } ?: ImageProvider(id)
}
