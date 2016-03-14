package games.angusgaming.anguspaint;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Harry on 3/13/2016.
 */
public class TextFragment extends DialogFragment {

    Context mContext;

    public TextFragment(){
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

        TextView tv = new TextView(getActivity());
        tv.setText(getArguments().getInt("stringID", R.string.null_null));
        tv.setTextSize(15);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);

        linLayout.addView(tv);

        return linLayout;
    }
}
