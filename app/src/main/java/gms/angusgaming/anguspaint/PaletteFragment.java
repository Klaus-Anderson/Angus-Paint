package gms.angusgaming.anguspaint;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Harry on 3/13/2016.
 */
public class PaletteFragment extends DialogFragment{
    Context mContext;
    public PaletteFragment() {

        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linLayout = new LinearLayout(getActivity());

        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setLayoutParams(new ActionBar
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout buttonLayout = new LinearLayout(getActivity());
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);

        ImageButton colorButton = new ImageButton(getActivity());
        colorButton.setImageResource(R.drawable.ic_palette_black_24dp);
        buttonLayout.addView(colorButton);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PaintActivity)PaletteFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .hide(PaletteFragment.this).commit();

                AmbilWarnaDialog colorPicker =
                        new AmbilWarnaDialog(getActivity(),
                                ((PaintActivity)getActivity()).getBrushColor(),
                                new AmbilWarnaDialog.OnAmbilWarnaListener(){
                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                // color is the color selected by the user.
                                ((PaintActivity)PaletteFragment.this.getActivity()).setColor(color);
                                ((PaintActivity)PaletteFragment.this.getActivity())
                                        .getFragmentManager().beginTransaction()
                                        .remove(PaletteFragment.this).commit();
                            }

                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {
                                // cancel was selected by the user
                                ((PaintActivity)PaletteFragment.this.getActivity())
                                        .getFragmentManager().beginTransaction()
                                        .remove(PaletteFragment.this).commit();
                            }
                        });
                colorPicker.show();
            }
        });

        ImageButton brushButton = new ImageButton(getActivity());
        brushButton.setImageResource(R.drawable.ic_brush_black_24dp);
        buttonLayout.addView(brushButton);

        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PaintActivity)PaletteFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .hide(PaletteFragment.this).commit();

                BrushSizeFragment brushFrag = new BrushSizeFragment();
                brushFrag.show(PaletteFragment.this.getActivity().getFragmentManager(), "Diag");

                ((PaintActivity)PaletteFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .remove(PaletteFragment.this).commit();
            }
        });

        linLayout.addView(buttonLayout);

        LinearLayout buttonNameLayout = new LinearLayout(getActivity());
        buttonNameLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonNameLayout.setGravity(Gravity.CENTER);
        buttonNameLayout.setLayoutParams(new ActionBar
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView colorTextView = new TextView(getActivity());
        colorTextView.setText(R.string.color_picker);
        colorTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        colorTextView.setTextSize(18);
        colorTextView.setTextColor(Color.BLACK);
        colorTextView.setLayoutParams(new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        buttonNameLayout.addView(colorTextView);

        TextView brushTextView = new TextView(getActivity());
        brushTextView.setText(R.string.brush_size);
        brushTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        brushTextView.setTextSize(18);
        brushTextView.setTextColor(Color.BLACK);
        brushTextView.setLayoutParams(new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        buttonNameLayout.addView(brushTextView);

        linLayout.addView(buttonNameLayout);



        return linLayout;
    }
}
