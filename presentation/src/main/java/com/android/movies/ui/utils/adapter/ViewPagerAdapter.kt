package com.android.movies.ui.utils.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * ViewPagerAdapter
 *
 * Simple ViewPager that holds Fragments
 *
 * @param manager FragmentManager
 *
 * @property fragmentList list of fragments
 * @property mFragmentTitleList list of fragments titles
 *
 */
class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val fragmentList = arrayListOf<Fragment>()
    private val mFragmentTitleList = arrayListOf<String>()

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int): String = mFragmentTitleList[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}