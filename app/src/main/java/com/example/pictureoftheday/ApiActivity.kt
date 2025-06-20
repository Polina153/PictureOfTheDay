package com.example.pictureoftheday

import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.example.pictureoftheday.databinding.ActivityApiBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import me.relex.circleindicator.CircleIndicator


class ApiActivity : AppCompatActivity() {

    private var _binding: ActivityApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityApiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.api_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        val indicator: CircleIndicator = view.findViewById<View>(R.id.indicator) as CircleIndicator
        indicator.setViewPager(binding.viewPager)
        indicator.createIndicators(3, 0)
        indicator.animatePageSelected(2)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        /* binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
         binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
         binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_system)*/

        //setCustomTabs()

        setHighlightedTab(EARTH)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })


    }

    private fun setCustomTabs() {
        val layoutInflater = LayoutInflater.from(this)
        binding.tabLayout.getTabAt(0)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        binding.tabLayout.getTabAt(1)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        binding.tabLayout.getTabAt(2)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }


    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@ApiActivity)

        binding.tabLayout.getTabAt(EARTH)?.customView = null
        binding.tabLayout.getTabAt(MARS)?.customView = null
        binding.tabLayout.getTabAt(WEATHER)?.customView = null

        when (position) {
            EARTH -> {
                setEarthTabHighlighted(layoutInflater)
            }

            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }

            WEATHER -> {
                setWeatherTabHighlighted(layoutInflater)
            }

            else -> {
                setEarthTabHighlighted(layoutInflater)
            }
        }
    }

    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
        val earth =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        binding.tabLayout.getTabAt(EARTH)?.customView = earth
        binding.tabLayout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        binding.tabLayout.getTabAt(WEATHER)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }

    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        binding.tabLayout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView = mars
        binding.tabLayout.getTabAt(WEATHER)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }

    private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
        val weather =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
        weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        binding.tabLayout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_earth, null)
        binding.tabLayout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        binding.tabLayout.getTabAt(WEATHER)?.customView = weather
    }

    companion object {
        private const val EARTH = 0
        private const val MARS = 1
        private const val WEATHER = 2
    }

}