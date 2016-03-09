package games.angusgaming.anguspaint;

/**
 * This Application is Basic Paint Application, for
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
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }
}
