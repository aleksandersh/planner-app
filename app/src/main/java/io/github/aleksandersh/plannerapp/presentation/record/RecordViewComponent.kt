package io.github.aleksandersh.plannerapp.presentation.record

import android.animation.LayoutTransition
import android.app.DatePickerDialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.utils.*
import java.util.*

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class RecordViewComponent(
    private val context: Context,
    private val viewModel: RecordViewModel
) : ViewComponent<ViewGroup>(R.id.record_creation_component) {

    private lateinit var dateButton: Button
    private lateinit var cycleView: View

    override fun buildView(): ViewGroup = with(context) {
        val dip4 = dip(4)
        val dip8 = dip(8)
        val dip16 = dip(16)
        return MaterialCardView(context).apply {
            cardElevation = dip4.toFloat()
            radius = dip4.toFloat()
            addView(
                ScrollView(context).apply {
                    addView(
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            layoutTransition = LayoutTransition()
                            dateButton = button {
                                setOnClickListener {
                                    showDatePickerDialog()
                                }
                            }
                            addView(
                                dateButton,
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip16, dip16, dip8)
                                }
                            )
                            addView(
                                TextInputLayout(context).apply {
                                    hint = "Title"
                                    addView(
                                        TextInputEditText(context).apply {
                                            id = R.id.record_creation_edit_text_title
                                            doOnTextChanged { text -> viewModel.title = text }
                                        }
                                    )
                                },
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip16, dip16, dip8)
                                }
                            )
                            addView(
                                TextInputLayout(context).apply {
                                    hint = "Description"
                                    addView(
                                        TextInputEditText(context).apply {
                                            id = R.id.record_creation_edit_text_description
                                            doOnTextChanged { text -> viewModel.description = text }
                                        }
                                    )
                                },
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip8, dip16, dip16)
                                }
                            )
                            addView(
                                SwitchMaterial(context).apply {
                                    text = "Repeat"
                                    textSize = 18f
                                    isChecked = viewModel.isRepeatSelected
                                    setOnCheckedChangeListener { _, isChecked ->
                                        viewModel.setRepeatSelected(isChecked)
                                    }
                                },
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip8, dip16, dip16)
                                }
                            )
                            cycleView = textView {
                                text = "CYCLE STUB"
                            }
                            addView(
                                cycleView,
                                linearLayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip8, dip16, dip8)
                                }
                            )
                            addView(
                                button {
                                    text = "Save"
                                    textSize = 16f
                                    setOnClickListener { viewModel.onClickSaveChanges() }
                                },
                                linearLayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip8, dip16, dip8)
                                    gravity = Gravity.END
                                }
                            )
                        },
                        FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    )
                },
                FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            )
        }
    }

    override fun onAttach() {
        viewModel.dateTitle.observe(this) { dateTitle ->
            dateButton.text = dateTitle
        }
        viewModel.isCycleShowed.observe(this) { isCycleShowed ->
            cycleView.visibility = if (isCycleShowed) View.VISIBLE else View.GONE
        }
    }

    private fun showDatePickerDialog() {
        val calendar = GregorianCalendar()
        calendar.time = viewModel.date
        DatePickerDialog( // TODO: DialogFragment
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                viewModel.setDate(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}