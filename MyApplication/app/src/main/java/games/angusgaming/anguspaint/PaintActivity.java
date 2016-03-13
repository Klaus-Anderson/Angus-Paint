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
 * • One spherical brush minimum with 3 different sizes
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class PaintActivity extends AppCompatActivity {

    private boolean isPortrait, hasDrawn, isLoad, wasLoad, willSave;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Screen Orientation will be decided upon creation of a new activity
        //The default orientation on opening the app the first time will be portrait
        Intent intent = getIntent();
        isPortrait = intent.getBooleanExtra("isPortrait", true);

        wasLoad = intent.getBooleanExtra("isLoad", false);

        hasDrawn = false;
        willSave = true;

        if(isPortrait)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(wasLoad){
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }


        // this code will set the apps content view as the
        // activity_paint layout xml
        setContentView(R.layout.activity_paint);

        // this code will set this Toolbar as the Action/App
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(wasLoad) {
            try {
                // When an Image is picked
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView after decoding the String
                    Drawable drawable = new BitmapDrawable(getResources(),BitmapFactory
                            .decodeFile(imgDecodableString));
                    this.findViewById(R.id.drawing).setBackground(drawable);

                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        }
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

            // this if/else statement will ask the user if they want to save
            // if they have drawn something and they have not saved since
            // their last paint stroke
            if(hasDrawn) {
                ContinueFragment contFrag = new ContinueFragment();
                contFrag.show(getFragmentManager(), "Cont");

            } else {
                OrientationFragment orientFrag = new OrientationFragment();
                orientFrag.show(getFragmentManager(), "Orient");
            }

            isLoad = true;

            return true;
        }
        //on save button click
        if (id == R.id.item_save) {
            savePainting();
            return true;
        }

        //on new button click
        if (id == R.id.item_new) {

            // this if/else statement will ask the user if they want to save
            // if they have drawn something and they have not saved since
            // their last paint stroke
            if(hasDrawn) {
                ContinueFragment contFrag = new ContinueFragment();
                contFrag.show(getFragmentManager(), "Cont");
            } else {
                OrientationFragment orientFrag = new OrientationFragment();
                orientFrag.show(getFragmentManager(), "Orient");
            }

            isLoad = false;

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

    @Override
    public void onStop(){
        // if the user closes the app with saving
        // their painting will autosave if it is not blank
        if(hasDrawn&&willSave)
            savePainting();

        super.onStop();
    }

    public void savePainting() {
        //Toast.makeText(this, "We've done it!", Toast.LENGTH_LONG).show();
        String imageName = UUID.randomUUID().toString()+".png";

        PaintView paintView = (PaintView) this.findViewById(R.id.drawing);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        File file = new File (myDir, imageName);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            paintView.getCanvasBitmap().compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

            Toast.makeText(this, "Painting saved as " + imageName, Toast.LENGTH_SHORT)
                    .show();
            hasDrawn = false;

        } catch (Exception e) {
            Toast.makeText(this, "Painting failed to save!", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

        paintView.destroyDrawingCache();

    }

    public void newPainting(boolean isPortraitCheck) {
        //Intent object is used to create and start a new Activity

        Intent paintIntent = new Intent(this, PaintActivity.class);

        // checks to see if user selected their painting
        // to be portrait or landscape
        paintIntent.putExtra("isPortrait", isPortraitCheck);

        // checks to see if new painting
        // is a load or a new
        paintIntent.putExtra("isLoad", isLoad);

        startActivity(paintIntent);
        this.finish();
    }

    public void setHasDrawn(boolean setter){
        hasDrawn = setter;
        //Toast.makeText(this, "We've done it!", Toast.LENGTH_SHORT).show();
    }

    public boolean getHasDrawn(){
        return hasDrawn;
    }

    public void setWillSave(boolean setter){
        willSave = setter;
    }

    public boolean getWasLoad(){
        return wasLoad;
    }

}
