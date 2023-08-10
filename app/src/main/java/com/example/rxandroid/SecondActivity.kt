package com.example.rxandroid

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.rxandroid.databinding.ActivitySecondBinding
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.util.IntentUtils

class SecondActivity : AppCompatActivity() {
    private val secondBinding: ActivitySecondBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_second)
    }
    private var mCameraUri: Uri? = null
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri = uri
                secondBinding.imgView.setLocalImage(uri, false)
            } else {
                parseError(it)
            }
        }
    private fun parseError(activityResult: ActivityResult) {
        if (activityResult.resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(activityResult.data), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       secondBinding.btnImage.setOnClickListener {
       pickCameraImage(View(this))
       }
    }
    fun pickCameraImage(view: View) {
        cameraLauncher.launch(
            ImagePicker.with(this)
                .crop()
                .cameraOnly()
                .maxResultSize(1080, 1920, true)
                .createIntent()
        )
    }

    fun showImage(view: View) {
        val uri = when (view) {

            secondBinding.imgView -> mCameraUri

            else -> null
        }

        uri?.let {
            startActivity(IntentUtils.getUriViewIntent(this, uri))
        }
    }

    fun ImageView.setDrawableImage(@DrawableRes resource: Int, applyCircle: Boolean = false) {
        val glide = Glide.with(this).load(resource).diskCacheStrategy(DiskCacheStrategy.NONE)
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(this)
        } else {
            glide.into(this)
        }
    }

    fun ImageView.setLocalImage(uri: Uri, applyCircle: Boolean = false) {
        val glide = Glide.with(this).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE)
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(this)
        } else {
            glide.into(this)
        }
    }
}