package br.com.luan.myskeletonv2.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ListView

/**
 * Created by luan silva on 19/04/18.
 */


fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

fun ListView.setListViewHeightBasedOnChildren() {
    val listAdapter = this.adapter ?: // pre-condition
            return

    var totalHeight = 0
    for (i in 0 until listAdapter.count) {
        val listItem = listAdapter.getView(i, null, this)
        listItem.measure(0, 0)
        totalHeight += listItem.measuredHeight
    }

    val params = this.layoutParams
    params.height = totalHeight + this.dividerHeight * (listAdapter.count - 1)
    this.layoutParams = params
    this.requestLayout()
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun View.updatePadding(paddingStart: Int = getPaddingStart(),
                       paddingTop: Int = getPaddingTop(),
                       paddingEnd: Int = getPaddingEnd(),
                       paddingBottom: Int = getPaddingBottom()) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}


fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }

