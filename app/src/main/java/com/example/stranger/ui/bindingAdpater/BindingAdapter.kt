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
        fun setImageUrl(view: ImageFilterView, url: String) {
            url?.let {
                Glide.with(view).load(it)
                    .into(view)
            }
        }

        @JvmStatic
        @androidx.databinding.BindingAdapter("app:url")
        fun setImageUrl(view: ShapeableImageView, url: String?) {
            if (url!= null){
                    Glide.with(view).load(url)
                        .into(view)
                }else{
                Glide.with(view).load(R.drawable.q)
                    .into(view)
            }



        }

        @JvmStatic
        fun setBitMap(view: ImageFilterView, bitmap: Bitmap?) {
            view.setImageBitmap(bitmap)
        }

    }

}