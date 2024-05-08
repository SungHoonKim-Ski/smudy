package com.ssafy.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseActivity
import com.ssafy.presentation.databinding.ActivityMainBinding
import com.ssafy.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private lateinit var permissionUtils: PermissionUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bnBar.setupWithNavController(navController)
        permissionCheck()
    }

    private fun permissionCheck() {
        permissionUtils = PermissionUtils(this, this)
        if (!permissionUtils.checkPermission()) permissionUtils.requestPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!permissionUtils.permissionResult(requestCode,permissions,grantResults)) permissionUtils.requestPermission()
    }
}