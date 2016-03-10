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
    // This Paint will hold the brush size and desired color information
    private Paint paintingPaint;

    // A Bitmap handles the which pixels handle which color.
    //
    // This is the Bitmap for the Canvas object
    private Bitmap canvasBitmap;

    // set the initial color as black
    private int paintColor = 0xFF660000;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpCanvas();
    }

    private void setUpCanvas() {

    }
}
