package gms.angusgaming.anguspaint;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class ContinueFragment extends DialogFragment {

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
        yesButton.setOnClickListener(v -> {
            // attempt to save the paint
            ((PaintActivity) ContinueFragment.this.getActivity()).savePainting();

            // only prompt the user to make a new painting
            // if the save is successful
            if(((PaintActivity) ContinueFragment.this.getActivity()).getHasNotDrawn()){
                ContinueFragment.this.getActivity()
                        .getSupportFragmentManager().beginTransaction()
                        .hide(ContinueFragment.this).commit();

                OrientationFragment orientFrag = new OrientationFragment();
                orientFrag.show(ContinueFragment.this.getActivity().getSupportFragmentManager(), "Diag");
            }

            ContinueFragment.this.getActivity()
                    .getSupportFragmentManager().beginTransaction()
                    .remove(ContinueFragment.this).commit();


        });

        Button noButton = new Button(getActivity());
        noButton.setText(R.string.no);
        noButton.setTextSize(18);
        buttonLayout.addView(noButton);

        //pressing No will create a new paint activity without saving
        noButton.setOnClickListener(v -> {
            // hide the ContinueFragment,
            // create a an OrientationFragment,
            // then remove the ContinueFragment
            ContinueFragment.this.getActivity()
                    .getSupportFragmentManager().beginTransaction()
                    .hide(ContinueFragment.this).commit();

            OrientationFragment orientFrag = new OrientationFragment();
            orientFrag.show(ContinueFragment.this.getActivity().getSupportFragmentManager(), "Diag");

            ContinueFragment.this.getActivity()
                    .getSupportFragmentManager().beginTransaction()
                    .remove(ContinueFragment.this).commit();
        });

        Button cancelButton = new Button(getActivity());
        cancelButton.setText(R.string.cancel);
        cancelButton.setTextSize(18);
        buttonLayout.addView(cancelButton);

        cancelButton.setOnClickListener(v -> ContinueFragment.this.getActivity()
            .getSupportFragmentManager().beginTransaction()
            .remove(ContinueFragment.this).commit());

        linLayout.addView(buttonLayout);

        return linLayout;
    }

}
