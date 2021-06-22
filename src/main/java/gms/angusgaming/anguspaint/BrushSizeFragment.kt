package gms.angusgaming.anguspaint

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.DialogFragment

/**
 * Created by Harry on 3/13/2016.
 */
class BrushSizeFragment : DialogFragment() {
    private var px = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val linLayout = LinearLayout(activity)
        linLayout.orientation = LinearLayout.VERTICAL
        linLayout.layoutParams = ActionBar.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val textView = TextView(activity)
        textView.setText(R.string.brush_size)
        textView.gravity = Gravity.CENTER
        textView.textSize = 18f
        textView.setTextColor(Color.BLACK)
        linLayout.addView(textView)
        val sliderLayout = LinearLayout(activity)
        sliderLayout.orientation = LinearLayout.HORIZONTAL
        sliderLayout.gravity = Gravity.CENTER
        sliderLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            500
        )
        val brushImage = ImageView(activity)
        brushImage.setImageResource(R.drawable.black_dot)
        val brushSizeText = TextView(activity)
        brushSizeText.gravity = Gravity.CENTER
        brushSizeText.setTextColor(Color.BLACK)
        brushSizeText.textSize = 18f
        val brushWidthSeek = SeekBar(activity)
        brushWidthSeek.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        brushWidthSeek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (this@BrushSizeFragment.activity != null) {
                    val displayMetrics = this@BrushSizeFragment.activity!!.resources.displayMetrics
                    // (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)==3.3
                    // may depend on screen
                    px =
                        Math.round(((progress * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT / 1.65)).toInt() + 1).toFloat())
                    brushSizeText.text = this@BrushSizeFragment.activity!!
                        .resources.getString(R.string.px, px.toString())
                    brushImage.layoutParams = LinearLayout.LayoutParams(px, px)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        brushWidthSeek.progress = ((activity as PaintActivity?)!!.brushSize / 2).toInt()
        sliderLayout.addView(brushWidthSeek)
        val brushLayout = LinearLayout(activity)
        brushLayout.orientation = LinearLayout.VERTICAL
        brushLayout.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        brushLayout.gravity = Gravity.CENTER
        brushLayout.addView(brushImage)
        brushLayout.addView(brushSizeText)
        sliderLayout.addView(brushLayout)
        val buttonLayout = LinearLayout(activity)
        buttonLayout.orientation = LinearLayout.HORIZONTAL
        buttonLayout.gravity = Gravity.CENTER
        val setButton = Button(activity)
        setButton.setText(R.string.set)
        setButton.textSize = 18f
        buttonLayout.addView(setButton)

        //pressing Yes will prompt the user to save the picture
        setButton.setOnClickListener { v: View? ->
            (activity as PaintActivity?)!!.brushSize = px.toFloat()
            this@BrushSizeFragment.activity!!
                .getSupportFragmentManager().beginTransaction()
                .remove(this@BrushSizeFragment).commit()
        }
        val cancelButton = Button(activity)
        cancelButton.setText(R.string.cancel)
        cancelButton.textSize = 18f
        buttonLayout.addView(cancelButton)

        //pressing No will create a new paint activity without saving
        cancelButton.setOnClickListener { v: View? ->
            this@BrushSizeFragment.activity!!
                .getSupportFragmentManager().beginTransaction()
                .remove(this@BrushSizeFragment).commit()
        }
        linLayout.addView(sliderLayout)
        linLayout.addView(buttonLayout)
        return linLayout
    }
}