package com.example.pictureoftheday

import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.pictureoftheday.databinding.ActivityAnimationsBinding
import com.example.pictureoftheday.databinding.ActivityAnimationsBonusEndBinding
import com.example.pictureoftheday.databinding.ActivityAnimationsBonusStartBinding

class AnimationsActivityBonus : AppCompatActivity() {
    private var show = false

    private var _binding: ActivityAnimationsBonusStartBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationsBonusStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backgroundImage.setOnClickListener { if (show) hideComponents() else showComponents() }
    }

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_end)

        val transition = ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 1200
        }


        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }

    private fun hideComponents() {
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_start)

        val transition = ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 1200
        }

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }
}
