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
import androidx.fragment.app.FragmentActivity
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

/**
 * Created by Harry on 3/13/2016.
 */
class PaletteFragment : DialogFragment() {
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

            addView(LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                addView(ImageButton(activity).apply {
                    setImageResource(R.drawable.ic_palette_black_24dp)
                    setOnClickListener { view: View ->
                        view.context.also {
                            (context as FragmentActivity)
                                .supportFragmentManager.beginTransaction()
                                .hide(this@PaletteFragment).commit()
                            AmbilWarnaDialog(
                                activity,
                                (activity as PaintActivity?)!!.brushColor,
                                object : OnAmbilWarnaListener {
                                    override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                                        // color is the color selected by the user.
                                        (this@PaletteFragment.activity as PaintActivity?)!!.setColor(color)
                                        this@PaletteFragment.activity!!
                                            .supportFragmentManager.beginTransaction()
                                            .remove(this@PaletteFragment).commit()
                                    }

                                    override fun onCancel(dialog: AmbilWarnaDialog) {
                                        // cancel was selected by the user
                                        this@PaletteFragment.activity!!
                                            .supportFragmentManager.beginTransaction()
                                            .remove(this@PaletteFragment).commit()
                                    }
                                }).apply {
                                show()
                            }
                        }
                    }
                })

                addView(ImageButton(activity).apply {
                    setImageResource(R.drawable.ic_brush_black_24dp)
                    setOnClickListener { v: View? ->
                        if (activity != null) {
                            this@PaletteFragment.activity!!
                                .supportFragmentManager.beginTransaction()
                                .hide(this@PaletteFragment).commit()
                            val brushFrag = BrushSizeFragment()
                            brushFrag.show(this@PaletteFragment.activity!!.supportFragmentManager, "Dialog")
                            this@PaletteFragment.activity!!
                                .supportFragmentManager.beginTransaction()
                                .remove(this@PaletteFragment).commit()
                        }
                    }
                })
            })

            addView(LinearLayout(activity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                layoutParams = ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                addView(TextView(activity).apply {
                    setText(R.string.color_picker)
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1F
                    )
                })

                addView(TextView(activity).apply {
                    setText(R.string.brush_size)
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1F
                    )
                })

            })
        }
    }
}