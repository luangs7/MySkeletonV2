package br.com.luan.myskeletonv2.view.ui.Tabs.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import br.com.squarebits.lausgirl.ui.view.fragments.LancamentoFragment
import br.com.squarebits.lausgirl.ui.view.fragments.PromocoesFragment
import br.com.squarebits.lausgirl.ui.view.fragments.TodosFragment
import android.support.v4.view.PagerAdapter



/**
 * Created by luan on 25/10/2016.
 */
class MainPagerAdapter(fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {
    var tab1: TodosFragment? = null
    var tab2: LancamentoFragment? = null
    var tab3: PromocoesFragment? = null
    var bundle:Bundle? = null



    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                tab1 = TodosFragment()
                return tab1
            }
            1 -> {
                tab2 = LancamentoFragment()
                return tab2
            }
            2 -> {
                tab3 = PromocoesFragment()
                return tab3
            }
            else -> return tab1
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }


}
