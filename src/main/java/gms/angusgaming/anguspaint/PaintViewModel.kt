package gms.angusgaming.anguspaint

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaintViewModel : ViewModel() {
    private val mutableBrushColorIntLiveData = MutableLiveData(Color.BLACK)
    private val mutableBrushSizeFloatLiveData = MutableLiveData(20f)
    val brushColorInt: LiveData<Int> get() = mutableBrushColorIntLiveData
    val brushSizeFloat: LiveData<Float> get() = mutableBrushSizeFloatLiveData

    fun setBrushColor(color: Int) {
        mutableBrushColorIntLiveData.value = color
    }

    fun setBrushSize(size: Float) {
        mutableBrushSizeFloatLiveData.value = size
    }

    fun getBrushSizeFloat(): Float? {
        return brushSizeFloat.value
    }

}