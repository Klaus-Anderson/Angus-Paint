package gms.angusgaming.anguspaint

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

/**
 * Created by Harry on 3/13/2016.
 */
class PaletteFragment : DialogFragment() {
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
        val buttonLayout = LinearLayout(activity)
        buttonLayout.orientation = LinearLayout.HORIZONTAL
        buttonLayout.gravity = Gravity.CENTER
        val colorButton = ImageButton(activity)
        colorButton.setImageResource(R.drawable.ic_palette_black_24dp)
        buttonLayout.addView(colorButton)
        colorButton.setOnClickListener { v: View? ->
            if (activity != null) {
                activity!!
                    .getSupportFragmentManager().beginTransaction()
                    .hide(this@PaletteFragment).commit()
                val colorPicker = AmbilWarnaDialog(
                    activity,
                    (activity as PaintActivity?)!!.brushColor,
                    object : OnAmbilWarnaListener {
                        override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                            // color is the color selected by the user.
                            (this@PaletteFragment.activity as PaintActivity?)!!.setColor(color)
                            this@PaletteFragment.activity!!
                                .getSupportFragmentManager().beginTransaction()
                                .remove(this@PaletteFragment).commit()
                        }

                        override fun onCancel(dialog: AmbilWarnaDialog) {
                            // cancel was selected by the user
                            this@PaletteFragment.activity!!
                                .getSupportFragmentManager().beginTransaction()
                                .remove(this@PaletteFragment).commit()
                        }
                    })
                colorPicker.show()
            }
        }
        val brushButton = ImageButton(activity)
        brushButton.setImageResource(R.drawable.ic_brush_black_24dp)
        buttonLayout.addView(brushButton)
        brushButton.setOnClickListener { v: View? ->
            if (activity != null) {
                this@PaletteFragment.activity!!
                    .getSupportFragmentManager().beginTransaction()
                    .hide(this@PaletteFragment).commit()
                val brushFrag = BrushSizeFragment()
                brushFrag.show(this@PaletteFragment.activity!!.supportFragmentManager, "Diag")
                this@PaletteFragment.activity!!
                    .getSupportFragmentManager().beginTransaction()
                    .remove(this@PaletteFragment).commit()
            }
        }
        linLayout.addView(buttonLayout)
        val buttonNameLayout = LinearLayout(activity)
        buttonNameLayout.orientation = LinearLayout.HORIZONTAL
        buttonNameLayout.gravity = Gravity.CENTER
        buttonNameLayout.layoutParams = ActionBar.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val colorTextView = TextView(activity)
        colorTextView.setText(R.string.color_picker)
        colorTextView.gravity = Gravity.CENTER_HORIZONTAL
        colorTextView.textSize = 18f
        colorTextView.setTextColor(Color.BLACK)
        colorTextView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        buttonNameLayout.addView(colorTextView)
        val brushTextView = TextView(activity)
        brushTextView.setText(R.string.brush_size)
        brushTextView.gravity = Gravity.CENTER_HORIZONTAL
        brushTextView.textSize = 18f
        brushTextView.setTextColor(Color.BLACK)
        brushTextView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        buttonNameLayout.addView(brushTextView)
        linLayout.addView(buttonNameLayout)
        return linLayout
    }
}