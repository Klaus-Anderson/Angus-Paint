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
    ): View {
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            addView(TextView(activity).apply {
                setText(R.string.which_orientation)
                gravity = Gravity.CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
            })
            addView(LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                addView(ImageButton(activity).apply {
                    setImageResource(R.drawable.ic_stay_current_portrait_black_24dp)
                    setOnClickListener { v: View? ->
                        if (activity != null) {
                            (activity as PaintActivity?)!!.setWillSave(false)
                            (this@OrientationFragment.activity as PaintActivity?)!!.newPainting(true)
                        }
                    }
                })
                addView(ImageButton(activity).apply {
                    setImageResource(R.drawable.ic_stay_current_landscape_black_24dp)
                    setOnClickListener { v: View? ->
                        if (activity != null) {
                            (activity as PaintActivity?)!!.setWillSave(false)
                            (activity as PaintActivity?)!!.newPainting(false)
                        }
                    }
                })
            })
        }
    }
}