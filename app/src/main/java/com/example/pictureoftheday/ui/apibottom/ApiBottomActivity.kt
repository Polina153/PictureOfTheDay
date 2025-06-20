package com.example.pictureoftheday.ui.apibottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.ActivityApiBottomBinding
import com.example.pictureoftheday.ui.api.EarthFragment
import com.example.pictureoftheday.ui.api.MarsFragment
import com.example.pictureoftheday.ui.api.WeatherFragment
import com.google.android.material.badge.BadgeDrawable

class ApiBottomActivity : AppCompatActivity() {

    private var _binding: ActivityApiBottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityApiBottomBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_earth)

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_mars)
        badge.number = 20

        val badge2 = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_weather)
        badge2.number = 4
        badge2.badgeGravity = BadgeDrawable.BOTTOM_END

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.bottom_view_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }

                else -> false
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
    }
}