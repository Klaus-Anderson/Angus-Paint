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
            addView(TextView(activity).apply {
                setText(R.string.wish_to_save)
                gravity = Gravity.CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
            })
            addView(LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                addView(Button(activity).apply {
                    setText(R.string.yes)
                    textSize = 18f

                    //pressing Yes will prompt the user to save the picture
                    setOnClickListener { v: View? ->
                        // attempt to save the paint
                        if (this@ContinueFragment.activity != null) {
                            (this@ContinueFragment.activity as PaintActivity?)!!.savePainting()

                            // only prompt the user to make a new painting
                            // if the save is successful
                            if (!(this@ContinueFragment.activity as PaintActivity?)!!.hasDrawn) {
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
                })

                addView(Button(activity).apply {
                    //pressing No will create a new paint activity without saving
                    setText(R.string.no)
                    textSize = 18f
                    setOnClickListener {
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
                })

                addView(Button(activity).apply {
                    setText(R.string.cancel)
                    textSize = 18f
                    setOnClickListener { v: View? ->
                        activity?.getSupportFragmentManager()?.beginTransaction()?.remove(this@ContinueFragment)
                            ?.commit()
                    }
                })
            })
        }
    }
}