package io.github.aleksandersh.plannerapp.presentation.planner

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.presentation.main.MainViewComponent
import io.github.aleksandersh.plannerapp.utils.provideViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class PlannerActivity : AppCompatActivity() {

    private lateinit var viewModel: PlannerViewModel
    private lateinit var mainComponent: ViewComponent<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = provideViewModel()
        mainComponent = MainViewComponent(this, viewModel.mainViewModel)
        mainComponent.attach(Lifecycle.State.CREATED)
        setContentView(mainComponent.view, FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))

        viewModel.finish.observe(this, Observer { if (it) finish() })
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

    override fun onBackPressed() {
        viewModel.onClickBack()
    }
}