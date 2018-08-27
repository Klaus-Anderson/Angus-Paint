package games.angusgaming.anguspaint;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Harry on 3/13/2016.
 */
public class BrushSizeFragment extends DialogFragment {
    Context mContext;
    private int px;
    public BrushSizeFragment() {

        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linLayout= new LinearLayout(getActivity());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setLayoutParams(new ActionBar
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(getActivity());
        textView.setText(R.string.brush_size);
        textView.setGravity(Gravity.CENTER);;
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);

        linLayout.addView(textView);

        LinearLayout sliderLayout= new LinearLayout(getActivity());
        sliderLayout.setOrientation(LinearLayout.HORIZONTAL);
        sliderLayout.setGravity(Gravity.CENTER);
        sliderLayout.setLayoutParams(new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                500));

        final ImageView brushImage = new ImageView(getActivity());
        brushImage.setImageResource(R.drawable.black_dot);

        final TextView brushSizeText = new TextView(getActivity());
        brushSizeText.setGravity(Gravity.CENTER);
        brushSizeText.setTextColor(Color.BLACK);
        brushSizeText.setTextSize(18);

        SeekBar brushWidthSeek = new SeekBar(getActivity());
        brushWidthSeek.setLayoutParams(new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        brushWidthSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DisplayMetrics displayMetrics = BrushSizeFragment.this.getActivity().
                        getResources().getDisplayMetrics();
                // (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)==3.3
                // may depend on screen
                px = Math.round((int) ((progress) * ((displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT) / 1.65)) + 1);
                brushSizeText.setText(px + "px");
                brushImage.setLayoutParams(new LinearLayout.LayoutParams(px, px));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brushWidthSeek.setProgress((int) (((PaintActivity) getActivity()).getBrushSize() / 2));
        sliderLayout.addView(brushWidthSeek);

        LinearLayout brushLayout= new LinearLayout(getActivity());
        brushLayout.setOrientation(LinearLayout.VERTICAL);
        brushLayout.setLayoutParams(new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        brushLayout.setGravity(Gravity.CENTER);

        brushLayout.addView(brushImage);
        brushLayout.addView(brushSizeText);

        sliderLayout.addView(brushLayout);

        LinearLayout buttonLayout = new LinearLayout(getActivity());
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);

        Button setButton = new Button(getActivity());
        setButton.setText(R.string.set);
        setButton.setTextSize(18);
        buttonLayout.addView(setButton);

        //pressing Yes will prompt the user to save the picture
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PaintActivity) getActivity()).setBrushSize(px);
                ((PaintActivity) BrushSizeFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .remove(BrushSizeFragment.this).commit();
            }
        });

        Button cancelButton = new Button(getActivity());
        cancelButton.setText(R.string.cancel);
        cancelButton.setTextSize(18);
        buttonLayout.addView(cancelButton);

        //pressing No will create a new paint activity without saving
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PaintActivity) BrushSizeFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .remove(BrushSizeFragment.this).commit();
            }
        });

        linLayout.addView(sliderLayout);

        linLayout.addView(buttonLayout);

        return linLayout;
    }

}
