package gms.angusgaming.anguspaint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Harry on 3/9/2016.
 *
 * After some research, I found the documentation on the Android Documentation on the Canvas class:
 * http://developer.android.com/reference/android/graphics/Canvas.html
 * This API helped me greatly in understanding how to properly paint to canvas
 *
 */
public class PaintView extends View {

    private boolean isCreated;

    // The Canvas class holds the "draw" calls.
    // To draw something, you need 4 basic components:
    // A Bitmap to hold the pixels,
    // a Canvas to host the draw calls (writing into the bitmap),
    // a drawing primitive (e.g. Rect, Path, text, Bitmap),
    // and a paint (to describe the colors and styles for the drawing).
    private Canvas drawCanvas;


    // The Path class encapsulates compound (multiple contour)
    // geometric paths consisting of straight line segments,
    // quadratic curves, and cubic curves.
    // It can be drawn with canvas.drawPath(path, paint),
    // either filled or stroked (based on the paint's Style), or it can be used for clipping or to draw text on a path
    //
    // this is the Path (drawing primitive type) which tracks the user's paint strokes
    private Path paintPath;

    // The Paint class holds the style and color information about how to draw geometries, text and bitmaps.
    //
    // The paintingPaint Paint object will hold the brush size and desired color information
    // The canvasPaint object will hold the Paint information for entire canvas
    private Paint paintingPaint, canvasPaint;

    // A Bitmap handles the which pixels handle which color.
    //
    // This is the Bitmap for the Canvas object
    private Bitmap canvasBitmap;

    //declare brush size float
    private float brushSize;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        brushSize = 20 ;
        setUpCanvas();

        isCreated = false;
    }

    //On creation of the view, will be called when the custom view is assigned a size
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(!isCreated) {
            super.onSizeChanged(w, h, oldw, oldh);

            // Possible bitmap configurations. A bitmap configuration describes how pixels are stored.
            // This affects the quality (color depth) as well as the ability to display transparent/translucent colors.
            canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            drawCanvas = new Canvas(canvasBitmap);

            // by default have the canvas be entirely white
            if (!((PaintActivity) getContext()).getWasLoad())
                whiteBackground();

            isCreated = true;
        }
    }

    public void whiteBackground() {
        drawCanvas.drawColor(Color.WHITE);
    }

    //Implement this to do your drawing.
    @Override
    protected void onDraw(Canvas canvas) {

        //Draw the specified bitmap, with its top/left corner at (x,y),
        //using the specified paint, transformed by the current matrix.
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);

        //Draw the specified path using the specified paint.
        canvas.drawPath(paintPath, paintingPaint);
    }

    //Implement this method to handle touch screen motion events.
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //this will tell the activity that something has been drawn,
        if(((PaintActivity) getContext()).getHasNotDrawn())
            ((PaintActivity)getContext()).setHasDrawn(true);

        // TouchX is x coordinate location of Touch event, relative to the PaintView
        float touchX = event.getX();
        // Touchy is y coordinate location of Touch event, relative to the PaintView
        float touchY = event.getY();

        switch (event.getAction()) {
            // MotionEvent.ACTION_DOWN is pressing down on screen
            case MotionEvent.ACTION_DOWN:
                paintPath.moveTo(touchX, touchY);
                break;
            // MotionEvent.ACTION_MOVE is pressing down on screen and moving finger
            case MotionEvent.ACTION_MOVE:
                paintPath.lineTo(touchX, touchY);
                break;
            // MotionEvent.ACTION_UP is lifting finger from the screen
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(paintPath, paintingPaint);
                paintPath.reset();
                break;
            default:
                return false;
        }
        //Calling invalidate will cause the onDraw method to execute.
        invalidate();
        return true;
    }

    private void setUpCanvas() {

        //set up Paint object settings
        //determining which methods to set is through combing of Paint class API
        //http://developer.android.com/reference/android/graphics/Paint.html

        //initialize Paint objects
        paintingPaint = new Paint();
        canvasPaint = new Paint();

        //set the Initial brush color as black
        paintingPaint.setColor(Color.BLACK);

        paintingPaint.setStrokeWidth(brushSize);

        //Set the paint's style, used for controlling how primitives' geometries are interpreted
        paintingPaint.setStyle(Paint.Style.STROKE);

        //set the paint's Join, used whenever the paint's style is Stroke or StrokeAndFill.
        paintingPaint.setStrokeJoin(Paint.Join.ROUND);

        // set the paint's line cap style, used whenever the paint's style is Stroke or StrokeAndFill.
        paintingPaint.setStrokeCap(Paint.Cap.ROUND);

        //initialize Path object
        paintPath = new Path();
    }

    public void setColor(int color){
        paintingPaint.setColor(color);
    }

    public Bitmap getCanvasBitmap(){
        return canvasBitmap;
    }

    public float getBrushSize(){
        return brushSize;
    }

    public void setBrushSize(float bSize){
        brushSize = bSize;
        paintingPaint.setStrokeWidth(brushSize);
    }

}
