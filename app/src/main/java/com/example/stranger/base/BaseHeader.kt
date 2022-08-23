package com.example.stranger.base


import android.content.Context
import android.graphics.drawable.AdaptiveIconDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.databinding.ToolbarBinding
import com.example.stranger.ui.SplashActivity

class BaseHeader : ConstraintLayout {
    var binding: ToolbarBinding? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.toolbar, this, false)
        addView(binding?.root)
    }

     fun setTitle(title: String) {
        binding?.title?.viewTreeObserver.apply {
            endTitleAnimation()
            android.os.Handler().postDelayed(Runnable {
                binding?.title?.text = title
                startTitleAnia()
            }, 300)
        }

    }

    private fun startTitleAnia() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.text_view_tranlate_end).apply {
            duration = 300
            start()
        }
        binding?.title?.startAnimation(animation)
    }

    private fun endTitleAnimation(){
        val animation = AnimationUtils.loadAnimation(context, R.anim.text_view_tranlate_strat).apply {
            duration = 300
            start()
        }
        binding?.title?.startAnimation(animation)
    }

    fun setIconRight(icon: Int){
        binding?.iconRightId?.setBackgroundResource(icon)
    }
    fun setIconLeft(icon: Int){
        binding?.iconLeftId?.setBackgroundResource(icon)
    }
    fun setOnClickButtonRight(onClick: ((View) ->Unit )){
        binding?.buttonRight?.setOnClickListener (onClick)
    }
    fun onBackPressed(activity: AppCompatActivity,onClick: ((View) ->Unit )? = null){
        binding?.iconLeftId?.setOnClickListener {
            activity.onBackPressed()
            binding?.buttonRight?.visibility = View.GONE
            onClick?.invoke(rootView)
        }
    }
    fun setTextRight(title: String? = null){
        binding?.buttonRight?.text = title
        visibleButtonRight(!title.isNullOrEmpty())
    }

    fun visibleButtonRight(isVisible :Boolean = false){
        binding?.buttonRight?.visibility =  if (isVisible) View.VISIBLE else View.GONE
    }

}