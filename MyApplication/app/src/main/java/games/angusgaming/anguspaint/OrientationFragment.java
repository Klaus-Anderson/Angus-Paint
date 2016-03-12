package games.angusgaming.anguspaint;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Harry on 3/11/2016.
 */
public class OrientationFragment extends DialogFragment {

    Context mContext;
    public OrientationFragment() {

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

        TextView orientationTextView = new TextView(getActivity());
        orientationTextView.setText(R.string.which_orientation);
        orientationTextView.setGravity(Gravity.CENTER);;
        orientationTextView.setTextSize(18);
        orientationTextView.setTextColor(Color.BLACK);

        linLayout.addView(orientationTextView);

        LinearLayout buttonLayout = new LinearLayout(getActivity());
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);

        ImageButton portraitButton = new ImageButton(getActivity());
        portraitButton.setImageResource(R.drawable.ic_stay_current_portrait_black_24dp);
        buttonLayout.addView(portraitButton);

        ImageButton landscapeButton = new ImageButton(getActivity());
        landscapeButton.setImageResource(R.drawable.ic_stay_current_landscape_black_24dp);
        buttonLayout.addView(landscapeButton);

        linLayout.addView(buttonLayout);

        return linLayout;
    }
}
