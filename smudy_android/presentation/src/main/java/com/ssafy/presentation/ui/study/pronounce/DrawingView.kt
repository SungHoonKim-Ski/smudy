package com.ssafy.presentation.ui.study.pronounce

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import com.ssafy.presentation.R
import kotlin.math.max

class DrawingView(context: Context, attr: AttributeSet? = null) : View(context, attr) {
    var onRequestCurrentAmplitude: (() -> Int)? = null

    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.user_graph)
        strokeWidth = LINE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }
    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.DrawingView,
            0, 0
        ).apply {
            try {
                amplitudePaint.color = getColor(R.styleable.DrawingView_amplitudeColor, context.getColor(R.color.user_graph))
            } finally {
                recycle()
            }
        }
    }
    private var drawingWidth: Int = 0
    private var drawingHeight: Int = 0
    private var drawingAmplitudes = mutableListOf<Int>()
    private var isReplaying: Boolean = false
    private var replayingPosition: Int = 0


    private val visualizeRepeatAction: Runnable = object : Runnable {
        override fun run() {
            if (!isReplaying) {
                val currentAmplitude = onRequestCurrentAmplitude?.invoke() ?: 0
                drawingAmplitudes.add(0, currentAmplitude)
            } else {
                replayingPosition++
            }

            invalidate()
            updateViewWidth()
            handler?.postDelayed(this, ACTION_INTERVAL)
        }
    }

    private fun updateViewWidth() {
        val minViewWidth = context.resources.displayMetrics.widthPixels // 스크린 너비
        val calculatedWidth =
            (LINE_SPACE * drawingAmplitudes.size + paddingLeft + paddingRight).toInt()  // 실제 그리는 너비 계산

        val newWidth = max(minViewWidth, calculatedWidth) // 최소 너비와 계산된 너비 중 큰 값 선택
        if (layoutParams != null && layoutParams.width != newWidth) {
            layoutParams.width = newWidth
            requestLayout() // 레이아웃 업데이트 요청
        }
    }

    private fun scrollToRight() {
        (parent as? HorizontalScrollView)?.let { scrollView ->
            scrollView.post {
                scrollView.scrollTo(scrollView.getChildAt(0).width, 0)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingWidth = w
        drawingHeight = h
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(layoutParams.width, heightMeasureSpec)
        scrollToRight()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = drawingHeight / 2f
        var offsetX = drawingWidth.toFloat()

        drawingAmplitudes
            .let { amplitudes ->
                if (isReplaying) {
                    amplitudes.takeLast(replayingPosition)
                } else {
                    amplitudes
                }
            }
            .forEach { amplitude ->
                val lineLength = amplitude / MAX_AMPLITUDE * drawingHeight * 0.8F

                offsetX -= LINE_SPACE
                if (offsetX < 0) return@forEach

                canvas.drawLine(
                    offsetX,
                    centerY - lineLength / 2F,
                    offsetX,
                    centerY + lineLength / 2F,
                    amplitudePaint
                )
            }
    }

    fun startVisualizing(isReplaying: Boolean) {
        this.isReplaying = isReplaying
        scrollToRight()
        handler?.post(visualizeRepeatAction)
    }

    fun stopVisualizing() {
        handler?.removeCallbacks(visualizeRepeatAction)
        replayingPosition = 0
    }

    fun clearVisualization() {
        drawingAmplitudes.clear()
        invalidate()
    }

    fun addAmplitude(amplitude: Int) {
        drawingAmplitudes.add(0, amplitude)
        invalidate()
        updateViewWidth()
    }

    companion object {
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15F
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
        private const val ACTION_INTERVAL = 20L
    }
}
