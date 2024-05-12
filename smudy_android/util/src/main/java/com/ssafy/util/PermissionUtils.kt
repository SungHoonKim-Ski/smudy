package com.ssafy.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtils(
    private val context: Context,
    private val activity: Activity
) {
    private val permissions = listOf(android.Manifest.permission.RECORD_AUDIO)
    private val remainingPermissions = mutableListOf<String>()

    fun checkPermission(): Boolean {
        permissions.map {
            if (ContextCompat.checkSelfPermission(
                    context,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) remainingPermissions.add(it)
        }
        return remainingPermissions.isEmpty()
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity,
            remainingPermissions.toTypedArray(),
            REQUEST_CODE
        )
    }

    fun permissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ): Boolean {
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty()) {
            grantResults.map {
                if (it == -1) return false
            }
        }
        return true
    }

    companion object {
        val REQUEST_CODE = 1227
    }
}