package com.ssafy.presentation.ui.study.fill

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.ssafy.presentation.databinding.DialogInputBinding
import com.ssafy.presentation.model.BlankQuestion
import com.ssafy.presentation.model.toQuestion
import com.ssafy.presentation.ui.history.dpToPx

private const val TAG = "InputDialog"

class InputDialog(private val context: Context, private val data: BlankQuestion) :
    DialogFragment() {

    private var _binding: DialogInputBinding? = null
    private val binding get() = _binding!!

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etInput.setText(data.toQuestion())
            etInput.requestFocus()
            (etInput.text as Spannable).setSpan(
                UnderlineSpan(), data.blankStart, data.blankEnd + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                etInput,
                InputMethodManager.SHOW_IMPLICIT
            )

            var origin = ""

            var isAdded = true
            var react = true

            etInput.apply {
                setSelection(data.blankStart)

                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        origin = s.toString()
                    }
                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        isAdded = (count != 0)
                    }

                    override fun afterTextChanged(it: Editable?) {
                        if (react) {
                            react = false
                            if (data.blankStart > -1) {
                                if (selectionStart < data.blankStart) {
                                    setText(origin)
                                    post {
                                        setSelection(data.blankStart)
                                    }
                                }else if(selectionEnd > data.blankEnd + 1){
                                    setText(origin)
                                    post {
                                        setSelection(data.blankEnd + 1)
                                    }
                                }else{
                                    val cur = selectionEnd
                                    val new = origin
                                    if(isAdded){
                                        setText(it.toString().removeRange(
                                            selectionEnd, selectionEnd+1
                                        ))
                                    }else{
                                        val inputs = it.toString().substring(data.blankStart, data.blankEnd) + ' '
                                        setText(new.replaceRange(data.blankStart, data.blankEnd+1, inputs))
                                    }
                                    post{
                                        setSelection(cur)
                                    }
                                }

                                (etInput.text as Spannable).setSpan(
                                    UnderlineSpan(), data.blankStart, data.blankEnd + 1,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )

                            }
                        } else {
                            react = true
                        }

                    }

                })

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        val params = windowManager.currentWindowMetrics.bounds
        dialog?.window?.apply {
            setLayout(params.width(), ViewGroup.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM)
        }

    }

    interface InputListener {
        fun onInput(input: String)
    }

    private lateinit var listener: InputListener

    fun setInputListener(listener: InputListener) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener.onInput(binding.etInput.text.toString().substring(data.blankStart, data.blankEnd+1))
        super.onDismiss(dialog)
    }

}