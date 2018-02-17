package com.android.movies.conditionWatcher.instructions

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.android.movies.R
import com.android.movies.conditionWatcher.Instruction
import com.android.movies.ui.utils.adapter.GenericAdapter


class PopularShowsEndlessInstruction : Instruction() {

    private val DEFAULT_LIMIT = 20
    override val description: String = "Waiting for popular shows recycler to get populated after" +
                                       " endless scroll is triggerd"

    override fun checkCondition(activity: AppCompatActivity): Boolean {
        val showsRecycler = activity.findViewById(R.id.shows_recycler) as RecyclerView
        return showsRecycler != null && (showsRecycler.adapter as GenericAdapter).itemCount > DEFAULT_LIMIT
    }
}