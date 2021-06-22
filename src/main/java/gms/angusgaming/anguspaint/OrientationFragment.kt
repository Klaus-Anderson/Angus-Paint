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

/**
 * Created by Harry on 3/11/2016.
 */
class OrientationFragment : DialogFragment() {
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
        val orientationTextView = TextView(activity)
        orientationTextView.setText(R.string.which_orientation)
        orientationTextView.gravity = Gravity.CENTER
        orientationTextView.textSize = 18f
        orientationTextView.setTextColor(Color.BLACK)
        linLayout.addView(orientationTextView)
        val buttonLayout = LinearLayout(activity)
        buttonLayout.orientation = LinearLayout.HORIZONTAL
        buttonLayout.gravity = Gravity.CENTER
        val portraitButton = ImageButton(activity)
        portraitButton.setImageResource(R.drawable.ic_stay_current_portrait_black_24dp)
        buttonLayout.addView(portraitButton)
        portraitButton.setOnClickListener { v: View? ->
            if (activity != null) {
                (activity as PaintActivity?)!!.setWillSave(false)
                (this@OrientationFragment.activity as PaintActivity?)!!.newPainting(true)
            }
        }
        val landscapeButton = ImageButton(activity)
        landscapeButton.setImageResource(R.drawable.ic_stay_current_landscape_black_24dp)
        buttonLayout.addView(landscapeButton)
        landscapeButton.setOnClickListener { v: View? ->
            if (activity != null) {
                (activity as PaintActivity?)!!.setWillSave(false)
                (activity as PaintActivity?)!!.newPainting(false)
            }
        }
        linLayout.addView(buttonLayout)
        return linLayout
    }
}