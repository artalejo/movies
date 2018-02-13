package com.android.movies.ui.utils.views

import android.content.Context
import android.support.annotation.StringDef
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.android.movies.R
import com.android.movies.ui.utils.GenericTextWatcher
import com.android.movies.ui.utils.addAfterTextChangedListener
import com.android.movies.ui.utils.extensions.showErrorIcon
import org.jetbrains.anko.childrenSequence


/**
 * This custom view behaves as a [TextInputLayout] with a [TextInputEditText] attached.
 *
 * The following attributes can be added via the xml
 *
 *  - inputType -> sets the [InputType] for the inner [TextInputEditText].
 *                 At the moment only [INPUT_TYPE_PASSWORD] and [INPUT_TYPE_EMAIL_ADDRESS]
 *                 are allowed.
 *
 *  - emptyError -> sets the empty error message that will be displayed after a fill validation
 *                  has occurred. By default is set to "Not filled".
 *
 *  - text -> sets the text value for the inner [TextInputEditText].
 *
 *  - validateIsEmptyWhileTyping -> when start typing if the [TextInputEditText] is empty
 *                                  displays an empty error message
 *
 *  - textColor -> sets the text color for the inner [TextInputEditText]
 *
 *  - textSize -> set the text size for the inner [TextInputEditText].
 *
 */
class CustomTextInputLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : TextInputLayout(context, attrs, defStyleAttr) {

    private var emptyError: String? = null
    private var validateIsEmptyWhileTyping = false

    companion object {

        private const val INPUT_TYPE_PASSWORD = "textPassword"
        private const val INPUT_TYPE_EMAIL_ADDRESS = "textEmailAddress"

        @StringDef(INPUT_TYPE_PASSWORD, INPUT_TYPE_EMAIL_ADDRESS)
        @Retention(AnnotationRetention.SOURCE)
        annotation class TextInputType
    }

    init {
        val textInputEditText = TextInputEditText(context)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT)
        textInputEditText.layoutParams = params
        textInputEditText.setSingleLine(true)

        addView(textInputEditText)

        defStyleAttr.let {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable
                    .CustomTextInputLayout,
                    defStyleAttr, 0)
            val text = attributes.getString(R.styleable.CustomTextInputLayout_text)
            textInputEditText.setText(text)
            emptyError = attributes.getString(R.styleable.CustomTextInputLayout_emptyError)
            validateIsEmptyWhileTyping = attributes.getBoolean(R.styleable
                    .CustomTextInputLayout_validateIsEmptyWhileTyping, false)

            @TextInputType val inputType = attributes.getString(R.styleable
                    .CustomTextInputLayout_inputType)

            when (inputType) {
                INPUT_TYPE_PASSWORD -> textInputEditText.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                INPUT_TYPE_EMAIL_ADDRESS -> textInputEditText.inputType =
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }

            val textColor = attributes.getColor(R.styleable.CustomTextInputLayout_textColor,
                            ContextCompat.getColor(context,R.color.colorPrimary))
            textInputEditText.setTextColor(textColor)
            val textSize = attributes.getDimension(R.styleable.CustomTextInputLayout_textSize,
                    context.resources.getDimension(R.dimen.default_text_size))
            textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

        if (emptyError == null) emptyError = context.getString(R.string.error_text_is_empty)

        isErrorEnabled = true
        textInputEditText.addTextChangedListener(GenericTextWatcher({
            if (validateIsEmptyWhileTyping) showEmptyValidationError()
            else invalidateValidationError()
        }))
    }

    /**
     * Adds a [TextWatcher] to the inner [TextInputEditText] to listen for text changes
     * @param textWatcher
     */
    fun addTextChangedListener(textWatcher: TextWatcher) { editText?.addTextChangedListener(textWatcher) }

    /**
     * Adds a method to be invoked after text is changed
     */
    fun addAfterTextChangedListener(functionToExecute: () -> Unit) {
        editText?.addAfterTextChangedListener(functionToExecute)
    }

    /**
     * @return text value of the inner [TextInputEditText].
     */
    fun getText() : String? = editText?.text?.toString()

    /**
     * Sets a text value for the inner [TextInputEditText].
     * @param text
     */
    fun setText(text: String) { editText?.setText(text) }

    /**
     * Sets the error validation message.
     * @param errorToShow error to be displayed
     */
    fun showValidationError(errorToShow: String) { showError(errorToShow) }

    /**
     * Checks if the inner [TextInputEditText] is empty an displays the error message.
     * @return true if inner [TextInputEditText] is not filled
     */
    fun showEmptyValidationError() : Boolean {
        if (editText?.text.isNullOrBlank()) {
            showError(emptyError!!)
            return true
        } else invalidateValidationError()
        return false
    }

    private fun invalidateValidationError() {
        error = null
        editText?.setCompoundDrawables(null, null, null, null)
    }

    private fun showError(errorToShow: String) {
        editText?.showErrorIcon()
        this.error = errorToShow
    }
}

fun View?.validateCustomTextInputLayoutFilledData() : Boolean {
    var checkData = true

    this?.childrenSequence()?.forEach { children ->
        if (children is CustomTextInputLayout && children.showEmptyValidationError())
            checkData = false
    }

    return checkData
}

