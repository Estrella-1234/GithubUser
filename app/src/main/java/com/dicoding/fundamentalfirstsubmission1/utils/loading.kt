package com.dicoding.fundamentalfirstsubmission1.utils

import android.view.View

class loading {

    fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}