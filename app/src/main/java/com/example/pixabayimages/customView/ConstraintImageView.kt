package com.example.pixabayimages.customView

import android.content.Context
import android.util.AttributeSet

class ConstraintImageView : androidx.appcompat.widget.AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This is the key that will make the height equivalent to its width
    }
}