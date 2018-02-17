package com.android.movies.conditionWatcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class Instruction {

    var dataContainer = Bundle()
        private set

    abstract val description: String

    fun setData(dataContainer: Bundle) {
        this.dataContainer = dataContainer
    }

    abstract fun checkCondition(activity: AppCompatActivity): Boolean
}