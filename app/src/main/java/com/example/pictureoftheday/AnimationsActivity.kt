package com.example.pictureoftheday

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.pictureoftheday.databinding.ActivityAnimationsBinding

class AnimationsActivity : AppCompatActivity() {

    private var _binding: ActivityAnimationsBinding? = null
    private val binding get() = _binding!!

    private var textIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAnimationsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.transitions_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,  Slide(Gravity.END))
            textIsVisible = !textIsVisible
            binding.text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }

    }
}