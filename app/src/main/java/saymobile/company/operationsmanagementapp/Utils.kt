package saymobile.company.operationsmanagementapp

import android.content.Context
import android.view.ContextThemeWrapper
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.StorageReference
import saymobile.company.operationsmanagementapp.model.GlideAPI
import saymobile.company.operationsmanagementapp.model.GlideApp

fun createMessageDialog(context: Context, title: String, message: String) : AlertDialog.Builder =
    AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_MaterialComponents_Light_Dialog_Alert))
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }


fun createLoadingDialog(context: Context, title: String) : AlertDialog.Builder =
    AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_MaterialComponents_Light_Dialog_Alert))
        .setTitle(title)
        .setView(ProgressBar(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT).apply { setPadding(64) }
        })
        .setCancelable(false)


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(imageRef: StorageReference, progressDrawable: CircularProgressDrawable) {
    // place holder not working with circular imageView
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)
        .centerCrop()
    GlideApp.with(this)
        .setDefaultRequestOptions(options)
        .load(imageRef)
        .into(this)
}
