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
import kotlin.math.roundToInt

/**
 * Created by Harry on 3/13/2016.
 */
class BrushSizeFragment : DialogFragment() {
    private var px = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val brushImage = ImageView(activity).apply {
            setImageResource(R.drawable.black_dot)
        }
        val brushSizeText = TextView(activity).apply {
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
            textSize = 18f
        }

        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            addView(TextView(activity).apply {
                setText(R.string.brush_size)
                gravity = Gravity.CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
            })
            addView(LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    500
                )
                addView(SeekBar(activity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1F
                    )
                    setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                            if (this@BrushSizeFragment.activity != null) {
                                val displayMetrics = this@BrushSizeFragment.activity!!.resources.displayMetrics
                                // (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)==3.3
                                // may depend on screen
                                px = ((progress * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT / 1.65))
                                    .toInt() + 1).toFloat().roundToInt()
                                brushSizeText.text = this@BrushSizeFragment.activity!!
                                    .resources.getString(R.string.px, px.toString())
                                brushImage.layoutParams = LinearLayout.LayoutParams(px, px)
                            }
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar) {}
                        override fun onStopTrackingTouch(seekBar: SeekBar) {}
                    })
                    progress = ((activity as PaintActivity?)!!.brushSize / 2).toInt()
                })
                addView(LinearLayout(activity).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1F
                    )
                    gravity = Gravity.CENTER
                    addView(brushImage)
                    addView(brushSizeText)
                })
            })
            addView(LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                //pressing Yes will prompt the user to save the picture
                addView(Button(activity).apply {
                    setText(R.string.set)
                    textSize = 18f
                    setOnClickListener { v: View? ->
                        (activity as PaintActivity?)!!.brushSize = px.toFloat()
                        this@BrushSizeFragment.activity!!
                            .supportFragmentManager.beginTransaction()
                            .remove(this@BrushSizeFragment).commit()
                    }
                })

                //pressing No will create a new paint activity without saving
                addView(Button(activity).apply {
                    setText(R.string.cancel)
                    textSize = 18f
                    setOnClickListener { v: View? ->
                        this@BrushSizeFragment.activity!!
                            .supportFragmentManager.beginTransaction()
                            .remove(this@BrushSizeFragment).commit()
                    }
                })
            } )
        }
    }
}