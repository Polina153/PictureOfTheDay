package com.example.pictureoftheday.ui.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pictureoftheday.AnimationsActivity
import com.example.pictureoftheday.AnimationsActivityBonus
import com.example.pictureoftheday.R
import com.example.pictureoftheday.RecyclerActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navigationView = requireView().findViewById<NavigationView>(R.id.navigation_view)
        val intentAnimations = Intent(requireActivity(), AnimationsActivity::class.java)
        val intentAnimationsBonus = Intent(requireActivity(), AnimationsActivityBonus::class.java)
        val intentRecycler = Intent(requireActivity(), RecyclerActivity::class.java)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> startActivity(intentAnimations)
                R.id.navigation_two -> startActivity(intentAnimationsBonus)
                R.id.navigation_three -> startActivity(intentRecycler)
            }
            true
        }
    }
}