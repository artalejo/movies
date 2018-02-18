package com.android.movies.ui.swipableShows.showDetail

import android.os.Bundle
import android.view.View
import com.android.movies.R
import com.android.movies.ui.base.BaseFragment
import com.android.movies.ui.entities.ShowViewEntity
import com.android.movies.ui.utils.extensions.load
import kotlinx.android.synthetic.main.fragment_show_detail.*
import javax.inject.Inject

class ShowDetailFragment : BaseFragment(), ShowDetailFragView {

    @Inject lateinit var presenter: SwipableShowsFragmentPresenter
    private lateinit var showInfo: ShowViewEntity

    override var layout = R.layout.fragment_show_detail

    companion object Fragment {
        private const val SHOW = "SHOW"

        @JvmStatic fun newInstance(album: ShowViewEntity): ShowDetailFragment {
            val fragment = ShowDetailFragment()
            val args = Bundle()
            args.putParcelable(SHOW, album)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfo = arguments?.getParcelable(SHOW) as ShowViewEntity
        close.setOnClickListener{ presenter.onBackBtnPressed() }
        setUpShowInfo()
    }

    private fun setUpShowInfo(){
        with (showInfo) {
            show_image.load(backdropPath)
            show_title.text = originalName
            show_date.text = firstAirDate
            show_overview.text = overview
            show_average.text = voteAverage.toString()
        }
    }

    override fun onBackPressed() { activity?.onBackPressed() }
}