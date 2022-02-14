package com.cognizant.caponeteambuild.utility

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.io.IOException

fun getJsonFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun setTextOrHide(newText: String?, textView: TextView) {
    if (newText.isNullOrBlank()) {
        textView.visibility = View.GONE
    } else {
        textView.text = newText
        textView.visibility = View.VISIBLE
    }
}

fun loadImage(imageUrl: String, imageView: ImageView) {
    Picasso.get()
        .load(imageUrl)
        .transform(RoundedCornersTransform())
        .into(imageView)
}

fun toCircularImage(imageUrl: Int, imageView: ImageView) {
    Picasso.get()
        .load(imageUrl)
        .transform(CircleTransform())
        .into(imageView)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}