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
    ): View? {
        val linLayout = LinearLayout(activity)
        linLayout.orientation = LinearLayout.VERTICAL
        linLayout.layoutParams = ActionBar.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val textView = TextView(activity)
        textView.setText(R.string.wish_to_save)
        textView.gravity = Gravity.CENTER
        textView.textSize = 18f
        textView.setTextColor(Color.BLACK)
        linLayout.addView(textView)
        val buttonLayout = LinearLayout(activity)
        buttonLayout.orientation = LinearLayout.HORIZONTAL
        buttonLayout.gravity = Gravity.CENTER
        val yesButton = Button(activity)
        yesButton.setText(R.string.yes)
        yesButton.textSize = 18f
        buttonLayout.addView(yesButton)

        //pressing Yes will prompt the user to save the picture
        yesButton.setOnClickListener { v: View? ->
            // attempt to save the paint
            if (this@ContinueFragment.activity != null) {
                (this@ContinueFragment.activity as PaintActivity?)!!.savePainting()

                // only prompt the user to make a new painting
                // if the save is successful
                if ((this@ContinueFragment.activity as PaintActivity?)!!.hasNotDrawn) {
                    (this@ContinueFragment.activity as PaintActivity)
                        .getSupportFragmentManager().beginTransaction()
                        .hide(this@ContinueFragment).commit()
                    val orientFrag = OrientationFragment()
                    orientFrag.show(
                        (this@ContinueFragment.activity as PaintActivity).getSupportFragmentManager(),
                        "Diag"
                    )
                }
                (this@ContinueFragment.activity as PaintActivity)
                    .getSupportFragmentManager().beginTransaction()
                    .remove(this@ContinueFragment).commit()
            }
        }
        val noButton = Button(activity)
        noButton.setText(R.string.no)
        noButton.textSize = 18f
        buttonLayout.addView(noButton)

        //pressing No will create a new paint activity without saving
        noButton.setOnClickListener { v: View? ->
            if (activity != null) {
                // hide the ContinueFragment,
                // create a an OrientationFragment,
                // then remove the ContinueFragment
                activity!!
                    .getSupportFragmentManager().beginTransaction()
                    .hide(this@ContinueFragment).commit()
                val orientFrag = OrientationFragment()
                orientFrag.show(this@ContinueFragment.activity!!.supportFragmentManager, "Diag")
                this@ContinueFragment.activity!!
                    .getSupportFragmentManager().beginTransaction()
                    .remove(this@ContinueFragment).commit()
            }
        }
        val cancelButton = Button(activity)
        cancelButton.setText(R.string.cancel)
        cancelButton.textSize = 18f
        buttonLayout.addView(cancelButton)
        cancelButton.setOnClickListener { v: View? ->
            activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this@ContinueFragment)
                ?.commit()
        }
        linLayout.addView(buttonLayout)
        return linLayout
    }
}