package games.angusgaming.anguspaint;

/**
 * This Application is Basic Paint Application
 *
 * We would like you to design a simple paint application.
 * This app should have a main activity with a canvas that allows the user to draw and save basic pictures/paintings.
 * The application should be able to run in portrait or landscape mode,
 * and should contain an ActionBar with a few buttons and a menu,
 * and should save a user’s painting in the event they accidentally close the app.
 * More speciﬁc core features are listed below, the rest is entirely up to you!
 *
 * Core Feature Requirements:
 * • Main activity with a large canvas for drawing
 * • ActionBar with at least three buttons: Load, Save, New
 * • ActionBar Menu which contains at least:
 * – “About” - Should bring up a dialog with some information about you/app
 * – “FAQ/Directions” - Should open a new activity with directions of how to use the app
 * • One spherical brush minimum with 3 di↵erent sizes
 * • Color choices (Red, Orange, Yellow, Green, Blue, Purple, White, Black)
 * • Save an image to the phone
 * • Load an image from phone storage and paint on it with the app
 * • Portrait and landscape functionality, and saved-state for switching between apps and back
 *
 * Suggested Bonus/Challenge Features:
 * • Share an image through text/email or other choices
 * • Various brush shapes and sizes
 * • Full color palette that allows any choice using RGB or HSV
 * • Capture a new image from camera to paint on
 *
 * I received assistance from this tutorial by Sue Smith to make a drawing app:
 * http://code.tutsplus.com/series/create-a-drawing-app-on-android--cms-704
 * I have thoroughly commented out the project to show my understanding of this app
 * goes beyond the tutorial, and the enhancements I will show my accelerated
 * understanding of Android App development.
 *
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PaintActivity extends AppCompatActivity {

    private boolean isPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Screen Orientation will be decided upon creation of a new activity
        //The default orientation on opening the app the first time will be portrait
        Intent intent = getIntent();
        isPortrait = intent.getBooleanExtra("isPortrait", true);

        if(isPortrait)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_paint);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //on load button click
        if (id == R.id.item_load) {
            return true;
        }
        //on save button click
        if (id == R.id.item_save) {
            this.savePainting();
            return true;
        }

        //on new button click
        if (id == R.id.item_new) {

            ContinueFragment contFrag = new ContinueFragment();
            contFrag.show(getFragmentManager(),"Diag");

            return true;
        }

        //on About button click
        if (id == R.id.item_about) {
            return true;
        }

        //on Faqs click
        if (id == R.id.item_faqs) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void savePainting() {
        //Toast.makeText(this, "We've done it!", Toast.LENGTH_LONG).show();
    }

    public void newPainting(boolean isPortraitCheck) {
        //Intent object is used to create and start a new Activity

        Intent paintIntent = new Intent(this, PaintActivity.class);

        // checks to see if user selected their painting
        // to be portrait or landscape
        if(isPortraitCheck)
            paintIntent.putExtra("isPortrait", true);
        else
            paintIntent.putExtra("isPortrait", false);

        startActivity(paintIntent);
        this.finish();

    }
}
