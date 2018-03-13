package br.com.luan.myskeletonv2.view.ui.Tabs

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.R.color.drawer
import br.com.luan.myskeletonv2.extras.CustomViewPager
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import br.com.luan.myskeletonv2.view.ui.Tabs.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : BaseDrawerActivity() {
    lateinit var adapter: MainPagerAdapter
    lateinit var viewPager: CustomViewPager
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        setSupportActionBar(toolbar)

        initView()
    }


    fun initView(){
        val tabLayout = findViewById(R.id.tab_layout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("TAB 1"))
        tabLayout.addTab(tabLayout.newTab().setText("TAB 2"))
        tabLayout.addTab(tabLayout.newTab().setText("TAB 3"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        adapter = MainPagerAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager = findViewById(R.id.pagerProducts) as CustomViewPager


        tabLayout.setupWithViewPager(viewPager)
        viewPager.setOffscreenPageLimit(2)
        viewPager.setAdapter(adapter)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                viewPager.setCurrentItem(tab.position)
//                adapter.getCurrentPage(viewPager.currentItem)


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


//        header = navigationView.inflateHeaderView(R.layout.nav_header_main)
//
//        header.setOnClickListener(View.OnClickListener { startActivity(ProfileActivity()) })
//
//
//        header.nomeMenu.text = user?.name


    }


}
