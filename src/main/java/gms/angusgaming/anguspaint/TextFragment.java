package gms.angusgaming.anguspaint;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Harry on 3/13/2016.
 */
public class TextFragment extends DialogFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linLayout = new LinearLayout(getActivity());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setLayoutParams(new ActionBar
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(getActivity());
        if (getArguments() != null) {
            tv.setText(getArguments().getInt("stringID", R.string.null_null));
            tv.setTextSize(15);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);

            linLayout.addView(tv);
        }
        return linLayout;
    }
}
