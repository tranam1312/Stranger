package com.example.stranger.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import com.google.android.material.R as RD
import com.example.stranger.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.ContentViewCallback

class CustomSnackBarContentLayout : LinearLayout, ContentViewCallback {
    private var messageView: AppCompatTextView? = null
    private var actionView: AppCompatButton? = null
    private var iconView: AppCompatImageView? = null
    private var maxInlineActionWidth = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onFinishInflate() {
        super.onFinishInflate()
        messageView = findViewById(R.id.tv_message)
        actionView = findViewById(R.id.tv_action)
        iconView = findViewById(R.id.im_action_left)
    }

    fun getMessageView(): AppCompatTextView? {
        return messageView
    }

    fun getActionView(): AppCompatButton? {
        return actionView
    }

    fun getIconView(): AppCompatImageView? {
        return iconView
    }

    fun updateActionTextColorAlphaIfNeeded(actionTextColorAlpha: Float) {
        if (actionTextColorAlpha != 1f) {
            val originalActionTextColor: Int? = actionView?.currentTextColor
            val colorSurface = MaterialColors.getColor(this, RD.attr.colorSurface)
            val actionTextColor =
                originalActionTextColor?.let {
                    MaterialColors.layer(
                        colorSurface,
                        it, actionTextColorAlpha
                    )
                }
            actionTextColor?.let { actionView?.setTextColor(it) }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (orientation == VERTICAL) {
            // The layout is by default HORIZONTAL. We only change it to VERTICAL when the action view
            // is too wide and ellipsizes the message text. When the condition is met, we should keep the
            // layout as VERTICAL.
            return
        }
        val multiLineVPadding =
            resources.getDimensionPixelSize(RD.dimen.design_snackbar_padding_vertical_2lines)
        val singleLineVPadding =
            resources.getDimensionPixelSize(RD.dimen.design_snackbar_padding_vertical)
        val isMultiLine: Boolean = messageView?.layout?.lineCount!! > 1
        var remeasure = false
        if (isMultiLine
            && maxInlineActionWidth > 0 && actionView?.measuredWidth!! > maxInlineActionWidth
        ) {
            if (updateViewsWithinLayout(
                    VERTICAL, multiLineVPadding, multiLineVPadding - singleLineVPadding
                )
            ) {
                remeasure = true
            }
        } else {
            val messagePadding = if (isMultiLine) multiLineVPadding else singleLineVPadding
            if (updateViewsWithinLayout(HORIZONTAL, messagePadding, messagePadding)) {
                remeasure = true
            }
        }
        if (remeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun updateViewsWithinLayout(
        orientation: Int, messagePadTop: Int, messagePadBottom: Int
    ): Boolean {
        var changed = false
        if (orientation != getOrientation()) {
            setOrientation(orientation)
            changed = true
        }
        if (messageView?.paddingTop != messagePadTop
            || messageView?.paddingBottom != messagePadBottom
        ) {
            messageView?.let { updateTopBottomPadding(it, messagePadTop, messagePadBottom) }
            changed = true
        }
        return changed
    }

    private fun updateTopBottomPadding(
        view: View, topPadding: Int, bottomPadding: Int
    ) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(
                view,
                ViewCompat.getPaddingStart(view),
                topPadding,
                ViewCompat.getPaddingEnd(view),
                bottomPadding
            )
        } else {
            view.setPadding(view.paddingLeft, topPadding, view.paddingRight, bottomPadding)
        }
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        messageView?.setAlpha(0f)
        messageView?.animate()?.alpha(1f)?.setDuration(duration.toLong())
            ?.setStartDelay(delay.toLong())
            ?.start()
        if (actionView?.visibility == VISIBLE) {
            actionView?.alpha = 0f
            actionView?.animate()?.alpha(1f)?.setDuration(duration.toLong())
                ?.setStartDelay(delay.toLong())?.start()
        }
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        messageView?.setAlpha(1f)
        messageView?.animate()?.alpha(0f)?.setDuration(duration.toLong())
            ?.setStartDelay(delay.toLong())
            ?.start()
        if (actionView?.visibility == VISIBLE) {
            actionView?.setAlpha(1f)
            actionView?.animate()?.alpha(0f)?.setDuration(duration.toLong())
                ?.setStartDelay(delay.toLong())?.start()
        }
    }

    fun setMaxInlineActionWidth(width: Int) {
        maxInlineActionWidth = width
    }
}
