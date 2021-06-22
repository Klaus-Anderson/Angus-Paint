package gms.angusgaming.anguspaint

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment

/**
 * Created by Harry on 3/13/2016.
 */
class TextFragment : DialogFragment() {
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
        val tv = TextView(activity)
        if (arguments != null) {
            tv.setText(arguments!!.getInt("stringID", R.string.null_null))
            tv.textSize = 15f
            tv.setTextColor(Color.BLACK)
            tv.gravity = Gravity.CENTER
            linLayout.addView(tv)
        }
        return linLayout
    }
}