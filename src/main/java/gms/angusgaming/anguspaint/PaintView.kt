package gms.angusgaming.anguspaint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by Harry on 3/9/2016.
 *
 * After some research, I found the documentation on the Android Documentation on the Canvas class:
 * http://developer.android.com/reference/android/graphics/Canvas.html
 * This API helped me greatly in understanding how to properly paint to canvas
 *
 */
class PaintView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var isCreated: Boolean

    // The Canvas class holds the "draw" calls.
    // To draw something, you need 4 basic components:
    // A Bitmap to hold the pixels,
    // a Canvas to host the draw calls (writing into the bitmap),
    // a drawing primitive (e.g. Rect, Path, text, Bitmap),
    // and a paint (to describe the colors and styles for the drawing).
    private var drawCanvas: Canvas? = null

    // The Path class encapsulates compound (multiple contour)
    // geometric paths consisting of straight line segments,
    // quadratic curves, and cubic curves.
    // It can be drawn with canvas.drawPath(path, paint),
    // either filled or stroked (based on the paint's Style), or it can be used for clipping or to draw text on a path
    //
    // this is the Path (drawing primitive type) which tracks the user's paint strokes
    private var paintPath: Path? = null

    // The Paint class holds the style and color information about how to draw geometries, text and bitmaps.
    //
    // The paintingPaint Paint object will hold the brush size and desired color information
    // The canvasPaint object will hold the Paint information for entire canvas
    private var paintingPaint: Paint? = null
    private var canvasPaint: Paint? = null

    // A Bitmap handles the which pixels handle which color.
    //
    // This is the Bitmap for the Canvas object
    var canvasBitmap: Bitmap? = null
        private set

    //declare brush size float
    internal var brushSize = 20f

    //On creation of the view, will be called when the custom view is assigned a size
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (!isCreated) {
            super.onSizeChanged(w, h, oldw, oldh)

            // Possible bitmap configurations. A bitmap configuration describes how pixels are stored.
            // This affects the quality (color depth) as well as the ability to display transparent/translucent colors.
            canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            drawCanvas = Canvas(canvasBitmap as Bitmap)

            // by default have the canvas be entirely white
            if (!(context as PaintActivity).wasLoad) whiteBackground()
            isCreated = true
        }
    }

    fun whiteBackground() {
        drawCanvas!!.drawColor(Color.WHITE)
    }

    //Implement this to do your drawing.
    override fun onDraw(canvas: Canvas) {

        //Draw the specified bitmap, with its top/left corner at (x,y),
        //using the specified paint, transformed by the current matrix.
        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)

        //Draw the specified path using the specified paint.
        canvas.drawPath(paintPath!!, paintingPaint!!)
    }

    //Implement this method to handle touch screen motion events.
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        //this will tell the activity that something has been drawn,
        if ((context as PaintActivity).hasNotDrawn) (context as PaintActivity).setHasDrawn(true)

        // TouchX is x coordinate location of Touch event, relative to the PaintView
        val touchX = event.x
        // Touchy is y coordinate location of Touch event, relative to the PaintView
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> paintPath!!.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> paintPath!!.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                drawCanvas!!.drawPath(paintPath!!, paintingPaint!!)
                paintPath!!.reset()
            }
            else -> return false
        }
        //Calling invalidate will cause the onDraw method to execute.
        invalidate()
        return true
    }

    private fun setUpCanvas() {

        //set up Paint object settings
        //determining which methods to set is through combing of Paint class API
        //http://developer.android.com/reference/android/graphics/Paint.html

        //initialize Paint objects
        paintingPaint = Paint()
        canvasPaint = Paint()

        //set the Initial brush color as black
        paintingPaint!!.color = Color.BLACK
        paintingPaint!!.strokeWidth = brushSize

        //Set the paint's style, used for controlling how primitives' geometries are interpreted
        paintingPaint!!.style = Paint.Style.STROKE

        //set the paint's Join, used whenever the paint's style is Stroke or StrokeAndFill.
        paintingPaint!!.strokeJoin = Paint.Join.ROUND

        // set the paint's line cap style, used whenever the paint's style is Stroke or StrokeAndFill.
        paintingPaint!!.strokeCap = Paint.Cap.ROUND

        //initialize Path object
        paintPath = Path()
    }

    fun setColor(color: Int) {
        paintingPaint!!.color = color
    }

    fun getBrushSize(): Float {
        return brushSize
    }

    fun setBrushSize(bSize: Float) {
        brushSize = bSize
        paintingPaint!!.strokeWidth = brushSize
    }

    init {
        setUpCanvas()
        isCreated = false
    }
}