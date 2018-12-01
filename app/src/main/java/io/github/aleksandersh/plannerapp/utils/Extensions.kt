package io.github.aleksandersh.plannerapp.utils

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Px
import androidx.core.view.updateMargins
import androidx.core.view.updateMarginsRelative
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

inline fun <reified VM : ViewModel> FragmentActivity.provideViewModel(): VM {
    return ViewModelProviders.of(this).get(VM::class.java)
}

inline fun TextView.doOnTextChanged(crossinline block: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            block(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

fun ViewGroup.MarginLayoutParams.updateMarginsRelativeCompat(
    @Px start: Int = -1,
    @Px top: Int = topMargin,
    @Px end: Int = -1,
    @Px bottom: Int = bottomMargin
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        val startCompat = if (start > -1) start else marginStart
        val endCompat = if (end > -1) end else marginEnd
        updateMarginsRelative(startCompat, top, endCompat, bottom)
    } else {
        val startCompat = if (start > -1) start else leftMargin
        val endCompat = if (end > -1) end else rightMargin
        updateMargins(startCompat, top, endCompat, bottom)
    }
}

inline fun <T> LiveData<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(lifecycleOwner, Observer { observer(it) })
}