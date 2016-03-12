package games.angusgaming.anguspaint;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ContinueFragment extends DialogFragment {
    Context mContext;
    public ContinueFragment() {

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
        textView.setText(R.string.wish_to_save);
        textView.setGravity(Gravity.CENTER);;
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);

        linLayout.addView(textView);

        LinearLayout buttonLayout = new LinearLayout(getActivity());
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER);

        Button yesButton = new Button(getActivity());
        yesButton.setText(R.string.yes);
        yesButton.setTextSize(18);
        buttonLayout.addView(yesButton);

        //pressing Yes will prompt the user to save the picture
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((PaintActivity) ContinueFragment.this.getActivity()).savePainting();
            }
        });

        Button noButton = new Button(getActivity());
        noButton.setText(R.string.no);
        noButton.setTextSize(18);
        buttonLayout.addView(noButton);

        //pressing No will create a new paint activity without saving
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide the ContinueFragment,
                // create a an OrientationFragment,
                // then remove the ContinueFragment
                ((PaintActivity)ContinueFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .hide(ContinueFragment.this).commit();

                OrientationFragment orientFrag = new OrientationFragment();
                orientFrag.show(getFragmentManager(), "Diag");

                ((PaintActivity)ContinueFragment.this.getActivity())
                        .getFragmentManager().beginTransaction()
                        .remove(ContinueFragment.this).commit();
            }
        });

        Button cancelButton = new Button(getActivity());
        cancelButton.setText(R.string.cancel);
        cancelButton.setTextSize(18);
        buttonLayout.addView(cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PaintActivity)ContinueFragment.this.getActivity())
                    .getFragmentManager().beginTransaction()
                    .remove(ContinueFragment.this).commit();
            }
        });

        linLayout.addView(buttonLayout);

        return linLayout;
    }

}
