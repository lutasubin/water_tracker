package com.weappsinc.watertracker.app.core.ads

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.graphics.toArgb
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.weappsinc.watertracker.app.core.theme.AppColors

private fun Context.dp(value: Int): Int =
    (value * resources.displayMetrics.density).toInt()

fun createNativeAdView(context: Context): NativeAdView {
    val badge = TextView(context).apply {
        text = "Ad"
        setPadding(context.dp(8), context.dp(4), context.dp(8), context.dp(4))
        background = GradientDrawable().apply {
            cornerRadius = context.dp(8).toFloat()
            setColor(AppColors.HomeProgressTrack.toArgb())
        }
        setTextColor(AppColors.HomePrimary.toArgb())
    }
    val icon = ImageView(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            context.dp(52),
            context.dp(52)
        ).apply { marginEnd = context.dp(12) }
        scaleType = ImageView.ScaleType.CENTER_CROP
    }
    val headline = TextView(context).apply {
        textSize = 16f
        setTextColor(AppColors.HomeTitle.toArgb())
    }
    val body = TextView(context).apply {
        textSize = 12f
        setTextColor(AppColors.HomeMuted.toArgb())
    }
    val copy = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        addView(badge)
        addView(headline)
        addView(body)
    }
    val cta = Button(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            context.dp(48)
        )
        setBackgroundColor(AppColors.HomePrimary.toArgb())
        setTextColor(AppColors.HomeCard.toArgb())
    }
    val header = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        setPadding(0, 0, 0, context.dp(12))
        addView(icon)
        addView(copy)
    }
    val content = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(context.dp(16), context.dp(16), context.dp(16), context.dp(16))
        background = GradientDrawable().apply {
            cornerRadius = context.dp(24).toFloat()
            setColor(AppColors.HomeCard.toArgb())
        }
        addView(header)
        addView(cta)
    }
    return NativeAdView(context).apply {
        addView(content)
        iconView = icon
        headlineView = headline
        bodyView = body
        callToActionView = cta
    }
}

fun bindNativeAdView(view: NativeAdView, nativeAd: NativeAd) {
    (view.iconView as ImageView).apply {
        setImageDrawable(nativeAd.icon?.drawable)
        visibility = if (nativeAd.icon?.drawable == null) View.GONE else View.VISIBLE
    }
    (view.headlineView as TextView).text = nativeAd.headline
    (view.bodyView as TextView).apply {
        text = nativeAd.body
        visibility = if (nativeAd.body.isNullOrBlank()) View.GONE else View.VISIBLE
    }
    (view.callToActionView as Button).apply {
        text = nativeAd.callToAction
        visibility = if (nativeAd.callToAction.isNullOrBlank()) View.GONE else View.VISIBLE
    }
    view.setNativeAd(nativeAd)
}
