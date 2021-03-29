package de.mobile.dinesh.gorillas.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any> Fragment.observe(liveData: LiveData<T>, body: (T) -> Unit) {
    liveData.observe(this.viewLifecycleOwner, Observer(body))
}