package com.example.ptyxiakh3

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Nullable


/**
 *
 * Title: com.example.zqd.testbezial
 *
 * Description:
 *
 * Copyright: Copyright (c) 2017
 *
 * Company:
 *
 * @author zhangqingdong
 * @date 2018/6/22 10:30
 */
class MyView @JvmOverloads constructor(
    context: Context?,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    View(context, attrs, defStyleAttr, defStyleRes) {

    private var wavePaint: Paint? = null

    private var circlePaint: Paint? = null

    private var textPaint: Paint? = null

    private var screenWidth = 0

    private var screenHeignt = 0

    private val amplitude = 40
    private var path: Path? = null

    private var progress = 0f
    private var textProgress = 0f

    private val startPoint = Point()
    private var textMiddle = ""

//edo allazo to pososto
    fun setProgress(progress: Float, text: String) {
        textProgress = progress
        if (progress == 100f) {
            this.progress = progress + amplitude
        } else {
            this.progress = progress
        }
        textMiddle= text
    }

    private fun init() {
        wavePaint = Paint()
        wavePaint!!.isAntiAlias = true
        wavePaint!!.strokeWidth = 1f
        textPaint = Paint()
        textPaint!!.style = Paint.Style.STROKE
        textPaint!!.isAntiAlias = true
        textPaint!!.color = Color.parseColor("#000000")
        textPaint!!.textSize = 50f
        circlePaint = Paint()
        circlePaint!!.isAntiAlias = true
        circlePaint!!.color = Color.parseColor("#292929")
        circlePaint!!.strokeWidth = 10f
        circlePaint!!.style = Paint.Style.STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measureSize(400, widthMeasureSpec), measureSize(400, heightMeasureSpec))
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w
        screenHeignt = h
        startPoint.x = -screenWidth
    }

    private fun clipSquare(canvas: Canvas) {
        val squarePath = Path()
        squarePath.addRect(0f, 0f, screenWidth.toFloat(), screenHeignt.toFloat(), Path.Direction.CW)
        canvas.clipPath(squarePath)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        clipSquare(canvas)

       // drawCircle(canvas)
       // drawSquare(canvas)

        drawWave(canvas)

        drawText(canvas)
        postInvalidateDelayed(10)
    }

    private fun drawSquare(canvas: Canvas) {

        canvas.drawRect(
            0F,
            0F,
            screenHeignt.toFloat(),
            screenHeignt.toFloat(),
            circlePaint!!
        )
    }




    private fun drawText(canvas: Canvas) {
        Log.d("sxolia","screenHeignt " + screenHeignt +"screenWidth " + screenWidth)
        val targetRect = Rect(0, -100 , 900, 0)
        val fontMetrics = textPaint!!.fontMetricsInt
        val baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        textPaint!!.textAlign = Paint.Align.CENTER
        canvas.drawText((textProgress).toString()+"%", targetRect.centerX().toFloat(), baseline.toFloat(), textPaint!!)

        val targetRect2 = Rect(0, -screenHeignt, screenWidth, 0)
        val fontMetrics2 = textPaint!!.fontMetricsInt
        val baseline2 = (targetRect2.bottom + targetRect2.top - fontMetrics2.bottom - fontMetrics2.top) / 2
        textPaint!!.textAlign = Paint.Align.CENTER
        canvas.drawText(textMiddle, targetRect2.centerX().toFloat(), baseline2.toFloat(), textPaint!!)
    }

    private fun drawWave(canvas: Canvas) {
        val height = (progress / 100 * screenHeignt).toInt()
        startPoint.y = -height
        canvas.translate(0f, screenHeignt.toFloat())
        path = Path()
        wavePaint!!.style = Paint.Style.FILL
        wavePaint!!.color = Color.parseColor("#FF0303")
        val wave = screenWidth / 4
        path!!.moveTo(startPoint.x.toFloat(), startPoint.y.toFloat())
        for (i in 0..3) {
            val startX = startPoint.x + i * wave * 2
            val endX = startX + 2 * wave
            if (i % 2 == 0) {
                path!!.quadTo(
                    ((startX + endX) / 2).toFloat(),
                    (startPoint.y + amplitude).toFloat(),
                    endX.toFloat(),
                    startPoint.y.toFloat()
                )
            } else {
                path!!.quadTo(
                    ((startX + endX) / 2).toFloat(),
                    (startPoint.y - amplitude).toFloat(),
                    endX.toFloat(),
                    startPoint.y.toFloat()
                )
            }
        }
        path!!.lineTo(screenWidth.toFloat(), (screenHeignt / 2).toFloat())
        path!!.lineTo(-screenWidth.toFloat(), (screenHeignt / 2).toFloat())
        path!!.lineTo(-screenWidth.toFloat(), 0f)
        path!!.close()
        canvas.drawPath(path!!, wavePaint!!)
        startPoint.x += 7
        if (startPoint.x > 0) {
            startPoint.x = -screenWidth
        }
        path!!.reset()
    }


    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(
            (screenHeignt / 2).toFloat(),
            (screenHeignt / 2).toFloat(),
            (screenHeignt / 2).toFloat(),
            circlePaint!!
        )
    }


    private fun clipCircle(canvas: Canvas) {
        val circlePath = Path()
        circlePath.addCircle(
            (screenWidth / 2).toFloat(),
            (screenHeignt / 2).toFloat(),
            (screenHeignt / 2).toFloat(),
            Path.Direction.CW
        )
        canvas.clipPath(circlePath)
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.UNSPECIFIED -> result = defaultSize
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> result = size
            else -> {}
        }
        return result
    }

    init {
        init()
    }
}