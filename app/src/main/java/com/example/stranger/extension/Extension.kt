package com.example.stranger.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*
import java.util.concurrent.TimeUnit

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun getTimeDate(): Long = System.currentTimeMillis()

internal fun randomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun convertToMMS(duration: String): String {
    val millis = duration.toLongOrNull()
    return String().format("%02d:%02d",
        millis?.let { TimeUnit.MILLISECONDS.toMinutes(it) % TimeUnit.MILLISECONDS.toMinutes(1) },
        millis?.let { TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MILLISECONDS.toSeconds(1) })
}


fun <K, V> HashMap<K, V>.toArrayList(hashMap: HashMap<K, V>): ArrayList<V> {
    val listKey = hashMap.keys
    val arrayList: ArrayList<V> = arrayListOf()
    for (key in listKey) {
        hashMap[key].apply {

        }
    }
    return arrayList
}

fun ImageFilterView?.setImageDrawable(icHeart: Int) {
    this?.context?.resources?.getDrawable(icHeart)
}
