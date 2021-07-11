package gms.angusgaming.anguspaint

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class ContinueFragment : DialogFragment() {
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

            (activity as? PaintActivity)?.let { paintActivity ->
                addView(TextView(paintActivity).apply {
                    setText(R.string.wish_to_start_a_new_painting)
                    gravity = Gravity.CENTER
                    textSize = 18f
                    setTextColor(Color.BLACK)
                })

                addView(LinearLayout(paintActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER

                    addView(Button(activity).apply {
                        setText(R.string.yes)
                        textSize = 18f

                        //pressing Yes will prompt the user to save the picture
                        setOnClickListener {
                            OrientationFragment().show(
                                paintActivity.supportFragmentManager,
                                "Dialog"
                            )
                            paintActivity.supportFragmentManager
                                .beginTransaction()
                                .remove(this@ContinueFragment)
                                .commit()
                        }
                    })

                    addView(Button(paintActivity).apply {
                        setText(R.string.cancel)
                        textSize = 18f
                        setOnClickListener {
                            paintActivity.supportFragmentManager
                                .beginTransaction()
                                .remove(this@ContinueFragment)
                                .commit()
                        }
                    })
                })
            }
        }
    }
}