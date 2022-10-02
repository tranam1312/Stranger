package com.example.stranger.ui.bindingAdpater

import android.graphics.Bitmap
import android.net.Uri
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.google.android.material.imageview.ShapeableImageView

class BindingAdapter {
    companion object {
        @JvmStatic
        fun setImageUri(view: ImageFilterView, uri: Uri?) {
            uri?.let {
                Glide.with(view).load(it)
                    .into(view)
            }

        }

        @JvmStatic
        @androidx.databinding.BindingAdapter("app:url")
        fun setImageUrl(view: ImageFilterView, url: String?) {
            Glide.with(view).load(url ?: R.drawable.q)
                .into(view)
        }

        @JvmStatic
        @androidx.databinding.BindingAdapter("app:url")
        fun setImageUrl(view: ShapeableImageView, url: String?) {
                Glide.with(view).load(url ?: R.drawable.q)
                    .into(view)

        }

        @JvmStatic
        fun setBitMap(view: ImageFilterView, bitmap: Bitmap?) {
            view.setImageBitmap(bitmap)
        }

    }

}