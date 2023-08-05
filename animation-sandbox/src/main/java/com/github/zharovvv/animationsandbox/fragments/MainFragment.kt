package com.github.zharovvv.animationsandbox.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.github.zharovvv.animationsandbox.R

class MainFragment : Fragment() {

    private val tweenAnimationExampleFragment = TweenAnimationExampleFragment()
    private val frameAnimationExampleFragment = FrameAnimationExampleFragment()
    private val propertyAnimationExampleFragment = PropertyAnimationExampleFragment()
    private val vectorAnimationExampleFragment = VectorAnimationExampleFragment()
    private val transitionAnimationExampleFragment = TransitionAnimationExampleFragment()

    private lateinit var rootContainer: ViewGroup

    private lateinit var toTweenAnimationsButton: Button
    private lateinit var toFrameAnimationButton: Button
    private lateinit var toPropertyAnimationButton: Button
    private lateinit var toVectorAnimationButton: Button
    private lateinit var toTransitionAnimationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Вызов этого метода меняет жизненный цикл фрагмента, а именно, он
         * убирает из него вызовы onCreate и onDestroy при пересоздании Activity. Теперь
         * при _пересоздании_ Activity этот фрагмент не будет уничтожен, и все его поля
         * сохранят свои значения. Но при этом остальные методы из жизненного цикла
         * Fragment будут вызваны, так что не возникнет проблем с заменой ресурсов в
         * зависимости от конфигурации.
         */
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { rootContainer = it }
        return inflater.inflate(R.layout.fragment_main_animation_sandbox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toTweenAnimationsButton = view.findViewById(R.id.tween_animation)
        toTweenAnimationsButton.setOnClickListener {
            transitionTo(tweenAnimationExampleFragment)
        }
        toFrameAnimationButton = view.findViewById(R.id.frame_animation)
        toFrameAnimationButton.setOnClickListener {
            transitionTo(frameAnimationExampleFragment)
        }
        toPropertyAnimationButton = view.findViewById(R.id.property_animation)
        toPropertyAnimationButton.setOnClickListener {
            transitionTo(propertyAnimationExampleFragment)
        }
        toVectorAnimationButton = view.findViewById(R.id.vector_animation)
        toVectorAnimationButton.setOnClickListener {
            transitionTo(vectorAnimationExampleFragment)
        }
        toTransitionAnimationButton = view.findViewById(R.id.transition_animation)
        toTransitionAnimationButton.setOnClickListener {
            transitionTo(transitionAnimationExampleFragment)
        }
        val toDeepDiveIntoAnimationButton = view.findViewById<Button>(R.id.deep_dive_into_animation)
        toDeepDiveIntoAnimationButton.setOnClickListener {
            transitionTo(DeepDiveIntoAnimationFragment())
        }
    }

    @SuppressLint("DefaultLocale")
    private fun transitionTo(fragment: Fragment) {
        val fragmentTag: String = fragment.javaClass.simpleName.decapitalize()
        requireFragmentManager().beginTransaction()
            .setCustomAnimations(
                R.animator.animation_enter,
                R.animator.animation_exit,
                R.animator.animation_pop_enter,
                R.animator.animation_pop_exit
            )
            .replace(
                rootContainer.id,
                fragment,
                fragmentTag
            )
            .addToBackStack(null)
            .commit()
    }
}