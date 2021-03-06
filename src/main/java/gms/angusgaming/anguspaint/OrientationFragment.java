package gms.angusgaming.anguspaint;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Harry on 3/11/2016.
 */
public class OrientationFragment extends DialogFragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linLayout = new LinearLayout(getActivity());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setLayoutParams(new ActionBar
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView orientationTextView = new TextView(getActivity());
        orientationTextView.setText(R.string.which_orientation);
        orientationTextView.setGravity(Gravity.CENTER);
        orientationTextView.setTextSize(18);
        orientationTextView.setTextColor(Color.BLACK);

        linLayout.addView(orientationTextView);

        LinearLayout buttonLayout = new LinearLayout(getActivity());
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);

        ImageButton portraitButton = new ImageButton(getActivity());
        portraitButton.setImageResource(R.drawable.ic_stay_current_portrait_black_24dp);
        buttonLayout.addView(portraitButton);

        portraitButton.setOnClickListener(v -> {
            if(getActivity()!=null) {
                ((PaintActivity) getActivity()).setWillSave(false);
                ((PaintActivity) OrientationFragment.this.getActivity()).newPainting(true);
            }
        });

        ImageButton landscapeButton = new ImageButton(getActivity());
        landscapeButton.setImageResource(R.drawable.ic_stay_current_landscape_black_24dp);
        buttonLayout.addView(landscapeButton);

        landscapeButton.setOnClickListener(v -> {
            if(getActivity()!=null) {
                ((PaintActivity) getActivity()).setWillSave(false);
                ((PaintActivity) getActivity()).newPainting(false);
            }
        });

        linLayout.addView(buttonLayout);

        return linLayout;
    }
}
