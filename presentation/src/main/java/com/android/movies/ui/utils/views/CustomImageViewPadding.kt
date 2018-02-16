package com.android.movies.ui.utils.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.android.movies.R
import kotlinx.android.synthetic.main.custom_imageview_padding.view.*


/**
 * CustomImageViewPadding
 *
 * Imageview with an internal padding. Useful for sizing and allocate images that don't have a
 * default size (vectorial images)
 *
 * @param context the type of a member in this group.
 * @param attrs the type of a member in this group.
 * @param defStyleAttr the type of a member in this group.
 * @property imagePadding image padding. Image size will result from [getHeight] and [getWidth]
 * minus [imagePadding]
 * @property imageSrc image resource
 * @constructor Creates an [CustomImageViewPadding].
 */
class CustomImageViewPadding @JvmOverloads constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : ConstraintLayout(context, attrs, defStyleAttr) {

    var imagePadding = 0
    var imageSrc = R.drawable.ic_menu_share

    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.custom_imageview_padding, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.CustomImageViewPadding, defStyleAttr, 0)
            imagePadding = typedArray.getDimensionPixelSize(
                    R.styleable.CustomImageViewPadding_image_padding, 0)
            imageSrc = typedArray.getResourceId(
                    R.styleable.CustomImageViewPadding_image_src,
                    R.drawable.ic_menu_share)
            icon.setImageResource(imageSrc)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val imageLayoutParams = icon.layoutParams.apply {
            height = heightMeasureSpec - imagePadding
            width = widthMeasureSpec - imagePadding
        }

        with(icon) {
            layoutParams = imageLayoutParams
            scaleType = ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}