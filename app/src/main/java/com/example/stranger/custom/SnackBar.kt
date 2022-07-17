package com.example.stranger.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.stranger.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.example.stranger.custom.CustomSnackBarContentLayout


class SnackBar : BaseTransientBottomBar<SnackBar> {

    companion object {
        private lateinit var content: CustomSnackBarContentLayout
        fun make(
            @Nullable context: Context,
            @NonNull view: View,
            @NonNull text: String,
            @Duration duration: Int, icon: Int? = null,
            action: Unit?
        ): SnackBar? {
            return makeInternal(context, view, text, duration, icon, action)
        }

        private fun makeInternal(
            @Nullable context: Context,
            @NonNull view: View,
            @NonNull text: String,
            @Duration duration: Int, icon: Int? = null, action: Unit? = null
        ): SnackBar? {
            val parent = findSuitableParent(view) ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )
            val context = parent?.context
            val inflater = LayoutInflater.from(context)
            content = inflater.inflate(
                R.layout.custom_snackbar, parent,
                false
            ) as CustomSnackBarContentLayout
            val snackbar = context?.let { SnackBar(it, parent, content, content) }
            return snackbar
        }


    private fun findSuitableParent(view: View): ViewGroup? {
        var view: View? = view
        var fallback: ViewGroup? = null
        do {
            if (view is CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                return view
            } else if (view is FrameLayout) {
                fallback = if (view.getId() == R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return view
                } else {
                    // It's not the content view but we'll use it as our fallback
                    view
                }
            }
            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                val parent = view.parent
                view = if (parent is View) parent else null
            }
        } while (view != null)

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback
    }
    }

    constructor(
        parent: ViewGroup,
        content: View,
        contentViewCallback: com.google.android.material.snackbar.ContentViewCallback
    ) : super(parent, content, contentViewCallback) {

    }

    constructor(
        context: Context,
        parent: ViewGroup,
        content: View,
        contentViewCallback: com.google.android.material.snackbar.ContentViewCallback
    ) : super(context, parent, content, contentViewCallback)

    fun setText(message: CharSequence): SnackBar {
        content.getMessageView()?.text = message
        return this
    }

    fun setText(@StringRes resId: Int): SnackBar {
        return setText(context.getText(resId))
    }

}