package gms.angusgaming.anguspaint

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaintViewModel : ViewModel() {
    private val mutableBrushColorInt = MutableLiveData(Color.BLACK)
    private val mutableBrushSizeFloat = MutableLiveData(20f)
    val brushColorInt: LiveData<Int> get() = mutableBrushColorInt
    val brushSizeFloat: LiveData<Float> get() = mutableBrushSizeFloat

    fun setBrushColor(color: Int) {
        mutableBrushColorInt.value = color
    }

    fun setBrushSize(size: Float) {
        mutableBrushSizeFloat.value = size
    }

    fun getBrushSizeFloat(): Float? {
        return brushSizeFloat.value
    }

}