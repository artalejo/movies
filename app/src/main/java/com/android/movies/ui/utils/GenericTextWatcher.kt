package com.android.movies.ui.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * This class is a custom [TextWatcher] that invokes a custom method after text changes have been
 * made.
 *
 * @param functionToExecute after text changes
 */
class GenericTextWatcher constructor(private val functionToExecute: () -> Unit) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) { functionToExecute() }
}

fun EditText.addAfterTextChangedListener(functionToExecute: () -> Unit) {
    this.addTextChangedListener(GenericTextWatcher(functionToExecute))
}