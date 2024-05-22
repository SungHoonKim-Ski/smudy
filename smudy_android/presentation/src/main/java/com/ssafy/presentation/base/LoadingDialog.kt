package com.ssafy.presentation.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.ssafy.presentation.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        setCancelable(false)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    fun showLoadingDialog() {
        if (!isShowing) {
            show()
        }
    }

    fun hideLoadingDialog() {
        if (isShowing) {
            dismiss()
        }
    }
}