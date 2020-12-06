package gms.angusgaming.anguspaint;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public class PaintActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 9001;
    private boolean hasDrawn, isLoad, wasLoad, willSave, isPortrait;
    private int brushColor;

    private static final int RESULT_LOAD_IMG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Screen Orientation will be decided upon creation of a new activity
        //The default orientation on opening the app the first time will be portrait
        Intent intent = getIntent();
        isPortrait = intent.getBooleanExtra("isPortrait", true);

        // checks to see if user selected their painting
        // to be portrait or landscape
        if (isPortrait)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //check to see if this painting is supposed to load a picture
        wasLoad = intent.getBooleanExtra("isLoad", false);

        hasDrawn = false;
        willSave = true;

        // if this is a load, open up an image application
        if (wasLoad) {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }

        brushColor = Color.BLACK;

        // this code will set the apps content view as the
        // activity_paint layout xml
        setContentView(R.layout.activity_paint);

        // this code will set this Toolbar as the Action/App
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                View canvas = this.findViewById(R.id.drawing);

                final Uri imageUri = data.getData();
                final InputStream imageStream;
                if (imageUri != null) {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    Matrix matrix = new Matrix();

                    matrix.postRotate(90);

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(selectedImage, canvas.getWidth(), canvas.getHeight(), true);

                    Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

                    // Set the Image in ImageView after decoding the String
                    Drawable drawable = new BitmapDrawable(getResources(), rotatedBitmap);

                    this.findViewById(R.id.drawing).setBackground(drawable);

                    willSave = true;
                } else {
                    Toast.makeText(this, "Failed to load image",
                            Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
                isLoad = false;
                newPainting(isPortrait);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            isLoad = false;
            newPainting(isPortrait);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPortrait)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.brush_editor) {
            PaletteFragment paletFrag = new PaletteFragment();
            paletFrag.show(getSupportFragmentManager(), "Cont");
        }

        //on load button click
        if (id == R.id.item_load) {
            // this if/else statement will ask the user if they want to save
            // if they have drawn something and they have not saved since
            // their last paint stroke
            if (hasDrawn) {
                ContinueFragment contFrag = new ContinueFragment();
                contFrag.show(getSupportFragmentManager(), "Cont");

            } else {
                OrientationFragment orientFrag = new OrientationFragment();
                orientFrag.show(getSupportFragmentManager(), "Orient");
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
            if (hasDrawn) {
                ContinueFragment contFrag = new ContinueFragment();
                contFrag.show(getSupportFragmentManager(), "Cont");
            } else {
                OrientationFragment orientFrag = new OrientationFragment();
                orientFrag.show(getSupportFragmentManager(), "Orient");
            }
            isLoad = false;
            return true;
        }
        //on About button click
        if (id == R.id.item_about) {
            TextFragment tFrag = new TextFragment();
            Bundle args = new Bundle();
            args.putInt("stringID", R.string.about_info);
            tFrag.setArguments(args);
            tFrag.show(getSupportFragmentManager(), "Cont");
            return true;
        }
        //on Faqs click
        if (id == R.id.item_faqs) {
            TextFragment tFrag = new TextFragment();
            Bundle args = new Bundle();
            args.putInt("stringID", R.string.faqs_info);
            tFrag.setArguments(args);
            tFrag.show(getSupportFragmentManager(), "Cont");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        // if the user closes the app with saving
        // their painting will auto save if it is not blank
        if (hasDrawn && willSave)
            savePainting();

        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        //noinspection SwitchStatementWithTooFewBranches
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePainting();
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void savePainting() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            return;
            // Permission is not granted
        }
        String imageName = UUID.randomUUID().toString() + ".png";

        PaintView paintView = this.findViewById(R.id.drawing);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/angus_paint");
        myDir.setReadable(true);
        if(!myDir.exists())
            myDir.mkdirs();

        File file = new File(myDir, imageName);

        try {
            if (!file.exists())
                file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            overlay(convertToBitmap(paintView.getBackground(),
                    paintView.getWidth(),
                    paintView.getHeight()),
                    paintView.getCanvasBitmap())
                    .compress(Bitmap.CompressFormat.PNG, 90, out);
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
    }

    public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    public void newPainting(boolean isPortraitCheck) {
        //Intent object is used to create and start a new Activity
        Intent paintIntent = new Intent(this, PaintActivity.class);

        paintIntent.putExtra("isPortrait", isPortraitCheck);

        // checks to see if new painting
        // is a load or a new
        paintIntent.putExtra("isLoad", isLoad);

        startActivity(paintIntent);
        this.finish();
    }

    public void setColor(int color) {
        brushColor = color;
        ((PaintView) findViewById(R.id.drawing)).setColor(color);
    }

    public int getBrushColor() {
        return brushColor;
    }

    public void setBrushSize(float bSize) {
        ((PaintView) findViewById(R.id.drawing)).setBrushSize(bSize);
    }

    public float getBrushSize() {
        return ((PaintView) findViewById(R.id.drawing)).getBrushSize();
    }

    public void setHasDrawn(boolean setter) {
        hasDrawn = setter;
    }

    public boolean getHasNotDrawn() {
        return !hasDrawn;
    }

    public void setWillSave(boolean setter) {
        willSave = setter;
    }

    public boolean getWasLoad() {
        return wasLoad;
    }
}
