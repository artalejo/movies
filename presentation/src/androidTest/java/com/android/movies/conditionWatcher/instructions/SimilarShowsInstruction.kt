package com.android.movies.conditionWatcher.instructions

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.android.movies.R
import com.android.movies.conditionWatcher.Instruction
import com.android.movies.ui.utils.adapter.GenericAdapter


class SimilarShowsInstruction : Instruction() {

    override val description: String = "Waiting for similar shows recycler to get populated"

    override fun checkCondition(activity: AppCompatActivity): Boolean {
        val similarShowsRecycler = activity.findViewById(R.id.similar_shows_recycler) as RecyclerView
        return similarShowsRecycler != null && (similarShowsRecycler.adapter as GenericAdapter).itemCount > 0
    }
}