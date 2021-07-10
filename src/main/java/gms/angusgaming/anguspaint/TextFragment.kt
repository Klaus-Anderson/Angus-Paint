package gms.angusgaming.anguspaint

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
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
    ): View {
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            addView(TextView(activity).apply {
                setText(arguments?.getInt("stringID", R.string.null_null) ?: R.string.null_null)
                textSize = 15f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                movementMethod = LinkMovementMethod.getInstance()
            })
        }
    }
}