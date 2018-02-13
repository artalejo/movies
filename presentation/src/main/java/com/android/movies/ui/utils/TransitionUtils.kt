package com.android.movies.ui.utils

import android.transition.Transition

fun Transition.onTransitionEnd(executeOnFinish: () -> Unit) {
    addListener(object: android.transition.Transition.TransitionListener {
        override fun onTransitionResume(transition: android.transition.Transition?) {}
        override fun onTransitionPause(transition: android.transition.Transition?) {}
        override fun onTransitionCancel(transition: android.transition.Transition?) {}
        override fun onTransitionStart(transition: android.transition.Transition?) {}
        override fun onTransitionEnd(transition: android.transition.Transition?) {
            executeOnFinish()
        }
    })

}