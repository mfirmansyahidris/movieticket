package com.dev.fi.movieticket

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*

import android.util.AttributeSet
import android.widget.LinearLayout



/**
 ****************************************
created by -manca-
.::manca.fi@gmail.com ::.
 ****************************************
 */

class TicketView : LinearLayout {
    private var bm: Bitmap? = null
    private var cv: Canvas? = null
    private var eraser: Paint? = null
    private val holesBottomMargin = 70
    private val holeRadius = 40

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        eraser = Paint()
        eraser!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        eraser!!.isAntiAlias = true
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            cv = Canvas(bm)
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val w = width
        val h = height

        bm!!.eraseColor(Color.TRANSPARENT)

        // set the view background color
        cv!!.drawColor(Color.WHITE)

        // drawing footer square contains the buy now button
        val paint = Paint()
        paint.setARGB(255, 250, 250, 250)
        paint.strokeWidth = 0F
        paint.style = Paint.Style.FILL
        cv!!.drawRect(0F, h.toFloat(), w.toFloat(), h - pxFromDp(context, holesBottomMargin.toFloat()), paint)

        // adding punching holes on the ticket by erasing them
        cv!!.drawCircle(0F, 0F, holeRadius.toFloat(), eraser) // top-left hole
        cv!!.drawCircle((w / 2).toFloat(), 0F, holeRadius.toFloat(), eraser) // top-middle hole
        cv!!.drawCircle(w.toFloat(), 0F, holeRadius.toFloat(), eraser) // top-right
        cv!!.drawCircle(0F, h - pxFromDp(context, holesBottomMargin.toFloat()), holeRadius.toFloat(), eraser) // bottom-left hole
        cv!!.drawCircle(w.toFloat(), h - pxFromDp(context, holesBottomMargin.toFloat()), holeRadius.toFloat(), eraser) // bottom right hole

        // drawing the image
        canvas.drawBitmap(bm, null, null)


        // drawing dashed lines at the bottom
        val mPath = Path()
        mPath.moveTo(holeRadius.toFloat(), h - pxFromDp(context, holesBottomMargin.toFloat()))
        mPath.quadTo((w - holeRadius).toFloat(), h - pxFromDp(context, holesBottomMargin.toFloat()), (w - holeRadius).toFloat(), h - pxFromDp(context, holesBottomMargin.toFloat()))

        // dashed line
        val dashed = Paint()
        dashed.setARGB(255, 200, 200, 200)
        dashed.style = Paint.Style.STROKE
        dashed.strokeWidth = 2F
        dashed.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        canvas.drawPath(mPath, dashed)

        super.onDraw(canvas)
    }

    companion object {

        fun pxFromDp(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }
    }
}