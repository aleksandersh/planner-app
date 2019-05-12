package io.github.aleksandersh.plannerapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import io.github.aleksandersh.plannerapp.presentation.base.ViewComponent

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

inline fun <T> ViewComponent<*>.observe(
    liveData: LiveData<T>,
    crossinline observer: (T?) -> Unit
) {
    liveData.observe(this, Observer { observer(it) })
}

inline fun <T> ViewComponent<*>.observeNonNull(
    liveData: LiveData<T>,
    crossinline observer: (T) -> Unit
) {
    liveData.observe(this, Observer { it?.let(observer) })
}