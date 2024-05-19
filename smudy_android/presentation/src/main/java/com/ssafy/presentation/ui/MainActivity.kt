package com.ssafy.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.log
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseActivity
import com.ssafy.presentation.databinding.ActivityMainBinding
import com.ssafy.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var permissionUtils: PermissionUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bnBar.setupWithNavController(navController)
        permissionCheck()
        registerObserve()
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
        if (!permissionUtils.permissionResult(
                requestCode,
                permissions,
                grantResults
            )
        ) permissionUtils.requestPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: activity")
        mainActivityViewModel.setResult(requestCode, resultCode, data)

    }

    private fun registerObserve() {
        with(mainActivityViewModel) {
            isNavigationBarVisible.observe(this@MainActivity){
                binding.bnBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
    }
}