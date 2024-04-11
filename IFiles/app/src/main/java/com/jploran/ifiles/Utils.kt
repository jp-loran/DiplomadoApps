package com.jploran.ifiles

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import android.view.inputmethod.InputMethodManager

fun Activity.miToast(text:String){
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}

fun Activity.sbMessage(view: View, text: String, textColor: String? = "#FFFFFF", bgColor: String? = "#9E1734", length: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(view, text, length)
        .setTextColor(Color.parseColor(textColor))
        .setBackgroundTint(Color.parseColor(bgColor))
        .show()
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}