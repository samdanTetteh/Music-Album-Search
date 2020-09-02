package com.ijikod.lastfm.Utilities

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/** Fragment extension function to **/
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

/**
 * Hide soft keyboard from screen
 * **/
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}