package io.github.aleksandersh.plannerapp.utils

import android.content.Context
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */

inline fun Context.linearLayout(builder: LinearLayout.() -> Unit): LinearLayout {
    val view = LinearLayout(this)
    view.builder()
    return view
}

inline fun Context.button(builder: Button.() -> Unit): Button {
    val view = Button(this)
    view.builder()
    return view
}

inline fun Context.textView(builder: TextView.() -> Unit): TextView {
    val view = TextView(this)
    view.builder()
    return view
}

inline fun linearLayoutParams(
    width: Int,
    height: Int,
    builder: LinearLayout.LayoutParams.() -> Unit
): LinearLayout.LayoutParams {
    val params = LinearLayout.LayoutParams(width, height)
    params.builder()
    return params
}

inline fun frameLayoutParams(
    width: Int,
    height: Int,
    builder: FrameLayout.LayoutParams.() -> Unit
): FrameLayout.LayoutParams {
    val params = FrameLayout.LayoutParams(width, height)
    params.builder()
    return params
}