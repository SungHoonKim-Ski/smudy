package com.ssafy.presentation.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.ssafy.presentation.R
import com.ssafy.presentation.base.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint



abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    protected val binding get() = _binding!!
    protected lateinit var _activity: Context
    private lateinit var loadingDialog: LoadingDialog
    protected lateinit var backPressedCallback: OnBackPressedCallback

    @SuppressLint("ResourceType")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = context
        loadingDialog = LoadingDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        binding.root.setOnClickListener {
            hideKeyboard()
        }
        return binding.root
    }

    fun hideKeyboard(){
        val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showToast(message: String) {
        Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String){
        Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
    }

    protected fun showLoading() {
        loadingDialog.showLoadingDialog()
    }

    protected fun hideLoading() {
        loadingDialog.hideLoadingDialog()
    }

    protected fun showExitConfirmationDialog(title: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setPositiveButton("종료") { dialog, _ ->
                dialog.dismiss()
                // Handle the positive button click, e.g., exit the fragment
                findNavController().popBackStack()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
                // Handle the negative button click, e.g., do nothing
            }.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.dark_red))
        }
        dialog.show()
    }

}