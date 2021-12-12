package com.github.zharovvv.animationsandbox.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.github.zharovvv.animationsandbox.R

/**
 * @see [R.layout.fragment_transition_animation_example]
 */
class TransitionAnimationExampleFragment : Fragment() {

    init {
        Log.i("fragmentLifeCycle", "constructor was called")
    }

//    private val transitionToL: Transition = AutoTransition()
//    private val transitionToR: Transition = AutoTransition()

    private lateinit var sceneRoot: ViewGroup
    private lateinit var imageViewActor: ImageView

    private lateinit var firstScene: Scene
    private lateinit var secondScene: Scene
    private lateinit var transitionManager: TransitionManager
    private lateinit var transitionToRListener: Transition.TransitionListener
    private lateinit var transitionToLListener: Transition.TransitionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("fragmentLifeCycle", "#onAttach(Context) was called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("fragmentLifeCycle", "#onCreate(Bundle) was called")
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("fragmentLifeCycle", "#onCreateView(LayoutInflater, ViewGroup, Bundle) was called")
        return inflater.inflate(R.layout.fragment_transition_animation_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("fragmentLifeCycle", "#onViewCreated(View, Bundle) was called")
        sceneRoot = view.findViewById(R.id.scene_root)
        transitionManager = TransitionInflater.from(view.context)
            .inflateTransitionManager(R.transition.transition_manager, sceneRoot)

        firstScene = Scene.getSceneForLayout(sceneRoot, R.layout.scene_first, view.context)
        firstScene.setEnterAction {
            imageViewActor = firstScene.sceneRoot.findViewById(R.id.scene_image_view)
            imageViewActor.setOnClickListener { transitionManager.transitionTo(secondScene) }
        }

        secondScene = Scene.getSceneForLayout(sceneRoot, R.layout.scene_second, view.context)
        secondScene.setEnterAction {
            imageViewActor = secondScene.sceneRoot.findViewById(R.id.scene_image_view)
            imageViewActor.setOnClickListener { transitionManager.transitionTo(firstScene) }
        }

        firstScene.enter()

//
//        transitionToRListener = object : Transition.TransitionListener {
//            override fun onTransitionEnd(transition: Transition) {
//                imageViewActor = sceneRoot.findViewById(R.id.scene_image_view)
//                imageViewActor.setOnClickListener {
//                    Log.d("DEBUG","press")
//                    TransitionManager.go(firstScene, transitionToL)
//                }
//            }
//
//            override fun onTransitionResume(transition: Transition) {
//            }
//
//            override fun onTransitionPause(transition: Transition) {
//            }
//
//            override fun onTransitionCancel(transition: Transition) {
//            }
//
//            override fun onTransitionStart(transition: Transition) {
//            }
//        }
//        transitionToR.addListener(transitionToRListener)
//
//        transitionToLListener = object : Transition.TransitionListener {
//            override fun onTransitionEnd(transition: Transition) {
//                imageViewActor = sceneRoot.findViewById(R.id.scene_image_view)
//                imageViewActor.setOnClickListener {
//                    Log.d("DEBUG","press")
//                    TransitionManager.go(secondScene, transitionToR)
//                }
//            }
//
//            override fun onTransitionResume(transition: Transition) {
//            }
//
//            override fun onTransitionPause(transition: Transition) {
//            }
//
//            override fun onTransitionCancel(transition: Transition) {
//            }
//
//            override fun onTransitionStart(transition: Transition) {
//            }
//        }
//        transitionToL.addListener(transitionToLListener)
//
//        imageViewActor = sceneRoot.findViewById(R.id.scene_image_view)
//        imageViewActor.setOnClickListener {
//            Log.d("DEBUG", "press")
//            transitionManager.transitionTo(secondScene)
////            TransitionManager.go(secondScene, transitionToR)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("fragmentLifeCycle", "#onDestroy() was called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("fragmentLifeCycle", "#onDetach() was called")
//        transitionToR.removeListener(transitionToRListener)
//        transitionToL.removeListener(transitionToLListener)
    }
}