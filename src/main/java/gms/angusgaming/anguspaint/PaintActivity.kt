package gms.angusgaming.anguspaint

import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.text.DateFormat
import java.util.*

class PaintActivity : AppCompatActivity() {

    companion object {
        private const val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 9001
    }

    private val viewModel: PaintViewModel by viewModels()

    private var isLoad = false
    var wasLoad = false
        private set
    private var willSave = false
    private var isPortrait = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.brushColorInt.observe(this) {
            findViewById<PaintView>(R.id.drawing).setColor(it)
        }

        viewModel.brushSizeFloat.observe(this) {
            findViewById<PaintView>(R.id.drawing).brushSize = it
        }

        //Screen Orientation will be decided upon creation of a new activity
        //The default orientation on opening the app the first time will be portrait
        isPortrait = intent.getBooleanExtra("isPortrait", true)

        // checks to see if user selected their painting
        // to be portrait or landscape
        requestedOrientation =
            if (isPortrait) ActivityInfo.SCREEN_ORIENTATION_PORTRAIT else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //check to see if this painting is supposed to load a picture
        wasLoad = intent.getBooleanExtra("isLoad", false)
        willSave = true

        // if this is a load, open up an image application
        if (wasLoad) {
            // Start the Intent
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                try {
                    // When an Image is picked
                    if (activityResult.resultCode == RESULT_OK) {
                        activityResult.data?.data?.also {
                            // Set the Image in ImageView after decoding the String
                            findViewById<View>(R.id.drawing).apply{
                                background =
                                    Bitmap.createScaledBitmap(
                                        BitmapFactory.decodeStream(contentResolver.openInputStream(it)),
                                        width, height, true).let{
                                        BitmapDrawable(resources,
                                            Bitmap.createBitmap(
                                                it, 0, 0, it.width, it.height,
                                                Matrix().apply {
                                                    postRotate(90f)
                                                },
                                                true
                                            ))
                                    }
                                willSave = true
                            }
                        } ?: Toast.makeText(
                            this, "Failed to load image",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this, "You haven't picked Image",
                            Toast.LENGTH_LONG
                        ).show()
                        isLoad = false
                        newPainting(isPortrait)
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                    isLoad = false
                    newPainting(isPortrait)
                }
            }.launch(
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ))
        }

        // this code will set the apps content view as the
        // activity_paint layout xml
        setContentView(R.layout.activity_paint)

        // this code will set this Toolbar as the Action/App
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            Log.d("CDA", "onKeyDown Called")
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        Log.d("CDA", "onBackPressed Called")
        startActivity(Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        requestedOrientation =
            if (isPortrait) ActivityInfo.SCREEN_ORIENTATION_PORTRAIT else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.brush_editor -> {
                PaletteFragment().show(supportFragmentManager, "Cont")
            }
            R.id.item_load -> {
                // this if/else statement will ask the user if they want to save
                // if they have drawn something and they have not saved since
                // their last paint stroke
                if (hasDrawn()) {
                    ContinueFragment().show(supportFragmentManager, "Cont")
                } else {
                    OrientationFragment().show(supportFragmentManager, "Orient")
                }
                isLoad = true
            }

            R.id.item_save -> {
                savePainting()
            }

            R.id.item_new -> {
                // this if/else statement will ask the user if they want to save
                // if they have drawn something and they have not saved since
                // their last paint stroke
                if (findViewById<PaintView>(R.id.drawing).hasDrawn) {
                    ContinueFragment().show(supportFragmentManager, "Cont")
                } else {
                    OrientationFragment().show(supportFragmentManager, "Orient")
                }
                isLoad = false
                return true
            }
            R.id.item_about -> {
                TextFragment().apply {
                    arguments = Bundle().apply {
                        putInt("stringID", R.string.about_info)
                    }
                }.show(supportFragmentManager, "About")
                return true
            }
            R.id.item_faqs -> {
                TextFragment().apply {
                    arguments = Bundle().apply {
                        putInt("stringID", R.string.faqs_info)
                    }
                }.show(supportFragmentManager, "FAQs")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onStop() {
        // if the user closes the app with saving
        // their painting will auto save if it is not blank
        if (hasDrawn() && willSave) savePainting()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    savePainting()
                }
            }
        }
    }

    fun savePainting() {
        val fileName = "ap_" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG)
            .format(Date()).replace(" ", "_").replace("/", "_")
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/angus-paint")
            }
        }

        val paintView = findViewById<PaintView>(R.id.drawing)

        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                try {
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    val paintViewBitmap = convertToBitmap(
                        paintView.background,
                        paintView.width,
                        paintView.height
                    )

                    Bitmap.createBitmap(paintViewBitmap.width, paintViewBitmap.height, paintViewBitmap.config).also{
                        Canvas(it).apply {
                            drawBitmap(paintViewBitmap, Matrix(), null)
                            drawBitmap(paintView.canvasBitmap, 0f, 0f, null)
                        }
                    }.compress(Bitmap.CompressFormat.PNG, 90, outputStream)

                    willSave = false
                    Toast.makeText(this, "Painting saved as Pictures/angus-paint/$fileName.png", Toast.LENGTH_LONG)
                        .show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Painting failed to save!", Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                } finally {
                    try {
                        outputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun convertToBitmap(drawable: Drawable, widthPixels: Int, heightPixels: Int): Bitmap {
        return Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888).also{
            drawable.setBounds(0, 0, widthPixels, heightPixels)
            drawable.draw(Canvas(it))
        }
    }

    fun newPainting(isPortraitCheck: Boolean) {
        //Intent object is used to create and start a new Activity
        Intent(this, PaintActivity::class.java).also{
            it.putExtra("isPortrait", isPortraitCheck)

            // checks to see if new painting
            // is a load or a new
            it.putExtra("isLoad", isLoad)
            startActivity(it)
        }

        finish()
    }


    fun setWillSave(setter: Boolean) {
        willSave = setter
    }

    fun hasDrawn(): Boolean {
        return findViewById<PaintView>(R.id.drawing).hasDrawn
    }
}