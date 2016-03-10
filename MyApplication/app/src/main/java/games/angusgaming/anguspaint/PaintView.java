package games.angusgaming.anguspaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
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

    // The Canvas class holds the "draw" calls.
    // To draw something, you need 4 basic components:
    // A Bitmap to hold the pixels,
    // a Canvas to host the draw calls (writing into the bitmap),
    // a drawing primitive (e.g. Rect, Path, text, Bitmap),
    // and a paint (to describe the colors and styles for the drawing).
    private Canvas paintCanvas;


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

    // set the initial color as black
    private int paintColor = 0xFFFFFF;

    //declare brush size float
    private float brushSize;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        brushSize = 20 ;

        setUpCanvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw the specified bitmap, with its top/left corner at (x,y),
        //using the specified paint, transformed by the current matrix.
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);

        //Draw the specified path using the specified paint.
        canvas.drawPath(paintPath, paintingPaint);
    }

    private void setUpCanvas() {

        //set up Paint object settings
        //determining which methods to set is through combing of Paint class API
        //http://developer.android.com/reference/android/graphics/Paint.html

        //intialize Paint objects
        paintingPaint = new Paint();
        canvasPaint = new Paint();

        paintingPaint.setColor(paintColor);
        paintingPaint.setStrokeWidth(brushSize);

        //Set the paint's style, used for controlling how primitives' geometries are interpreted
        paintingPaint.setStyle(Paint.Style.STROKE);

        //set the paint's Join, used whenever the paint's style is Stroke or StrokeAndFill.
        paintingPaint.setStrokeJoin(Paint.Join.ROUND);

        // set the paint's line cap style, used whenever the paint's style is Stroke or StrokeAndFill.
        paintingPaint.setStrokeCap(Paint.Cap.ROUND);

        //intialize Path object
        paintPath = new Path();
    }
}
