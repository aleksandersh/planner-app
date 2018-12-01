package io.github.aleksandersh.plannerapp.presentation.example

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.app.CachedDimens
import io.github.aleksandersh.plannerapp.app.PlannerApp
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.presentation.ViewNavigator
import io.github.aleksandersh.plannerapp.presentation.example.model.ScreenVm
import io.github.aleksandersh.plannerapp.utils.*

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class MainActivity : AppCompatActivity() {

    private lateinit var dimens: CachedDimens
    private lateinit var viewModel: MainViewModel
    private lateinit var mainComponent: ViewComponent<ViewGroup>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dimens = (application as PlannerApp).dimens
        viewModel = provideViewModel()

        val mainLayoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        mainComponent = object : ViewComponent<ViewGroup>() {

            override fun buildView(): ViewGroup {
                return FrameLayout(this@MainActivity)
            }
        }

        val component = getCreateRecordComponent()
        mainComponent.view.addView(
            component.view,
            LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        )
        mainComponent.bind(component)
        mainComponent.attach(Lifecycle.State.CREATED)

        setContentView(mainComponent.view, mainLayoutParams)
    }

    override fun onStart() {
        super.onStart()
        mainComponent.moveToState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        mainComponent.moveToState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()
        mainComponent.moveToState(Lifecycle.State.STARTED)
    }

    override fun onStop() {
        super.onStop()
        mainComponent.moveToState(Lifecycle.State.CREATED)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainComponent.detach()
    }

    private fun getCreateRecordComponent(): ViewComponent<*> {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val textViewTitle = TextView(this).apply {
            textSize = 18f
            setText(R.string.app_name)
        }

        layout.addView(
            textViewTitle,
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                updateMarginsRelativeCompat(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
                gravity = Gravity.CENTER_HORIZONTAL
            })

        val textViewStepsCount = TextView(this).apply {
            textSize = 16f
        }

        layout.addView(
            textViewStepsCount,
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                updateMarginsRelativeCompat(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
                gravity = Gravity.CENTER_HORIZONTAL
            })

        return object : ViewComponent<ViewGroup>() {

            private val navigator = ViewNavigator(this, layout.childCount)

            private val stepsCountObserver = Observer<Int> { count ->
                textViewStepsCount.text = "Moved forward $count times"
            }
            private val screenObserver = Observer<ScreenVm> { screen ->
                when (screen) {
                    is ScreenVm.Screen1 -> navigator.navigate(
                        getComponent1(screen.title),
                        LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    )
                    is ScreenVm.Screen2 -> navigator.navigate(
                        getComponent3(screen.title),
                        LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    )
                }
            }

            override fun buildView(): ViewGroup {
                return layout
            }

            override fun onAttach() {
                viewModel.navigator.observe(this, screenObserver)
                viewModel.stepsCount.observe(this, stepsCountObserver)
            }
        }
    }

    private fun getComponent1(titleText: String): ViewComponent<*> {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val titleTextView = TextView(this).apply {
            textSize = 16f
            text = titleText
        }

        layout.addView(
            titleTextView,
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                updateMarginsRelativeCompat(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
            }
        )

        val buttonsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        layout.addView(buttonsLayout, LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))

        val buttonMoveNext = Button(this).apply {
            text = "Go next"
            setOnClickListener { viewModel.onClickMoveNext() }
        }
        buttonsLayout.addView(
            buttonMoveNext,
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                updateMarginsRelativeCompat(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
                gravity = Gravity.CENTER_HORIZONTAL
            })

        return object : ViewComponent<ViewGroup>() {

            override fun buildView(): ViewGroup {
                return layout
            }
        }
    }

    private fun getComponent3(titleText: String): ViewComponent<*> {
        return object : ViewComponent<ViewGroup>() {

            override fun buildView(): ViewGroup {
                return linearLayout {
                    orientation = LinearLayout.VERTICAL
                    addView(
                        textView {
                            textSize = 16f
                            text = titleText
                        },
                        linearLayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                            setMargins(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
                        }
                    )
                    addView(
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            addView(
                                button {
                                    text = "Go back"
                                    setOnClickListener { viewModel.onClickMoveBack() }
                                },
                                linearLayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                                    setMargins(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
                                    gravity = Gravity.CENTER_HORIZONTAL
                                })
                            addView(
                                button {
                                    text = "Go next"
                                    setOnClickListener { viewModel.onClickMoveNext() }
                                },
                                linearLayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                                    setMargins(dimens.dip16, dimens.dip8, dimens.dip16, dimens.dip8)
                                    gravity = Gravity.CENTER_HORIZONTAL
                                })
                        },
                        LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    )
                }
            }
        }
    }
}