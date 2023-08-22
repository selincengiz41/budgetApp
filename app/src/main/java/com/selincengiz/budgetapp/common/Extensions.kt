package com.selincengiz.budgetapp.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.selincengiz.budgetapp.R
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.FileNotFoundException

object Extensions {

    fun Uri.getBitmapFromUri(context: Context): Bitmap? {
        try {
            val inputStream = context.contentResolver.openInputStream(this)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun ImageView.loadUrl(url: Uri?) {

        Glide.with(this.context).load(url).circleCrop().into(this)

    }
}