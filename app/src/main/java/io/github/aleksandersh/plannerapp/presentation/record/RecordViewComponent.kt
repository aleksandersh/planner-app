package io.github.aleksandersh.plannerapp.presentation.record

import android.animation.LayoutTransition
import android.app.DatePickerDialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.records.model.Record
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
    private lateinit var cycleView: EditText
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var repeatSwitch: SwitchMaterial

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
                                setOnClickListener { viewModel.onClickChooseDate() }
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
                                    titleEditText = TextInputEditText(context).apply {
                                        doOnTextChanged(viewModel::setTitle)
                                    }
                                    addView(
                                        titleEditText,
                                        LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                                    )
                                },
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip16, dip16, dip8)
                                }
                            )
                            addView(
                                TextInputLayout(context).apply {
                                    hint = "Description"
                                    descriptionEditText = TextInputEditText(context).apply {
                                        doOnTextChanged(viewModel::setDescription)
                                    }
                                    addView(
                                        descriptionEditText,
                                        LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                                    )
                                },
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip8, dip16, dip16)
                                }
                            )
                            repeatSwitch = SwitchMaterial(context).apply {
                                text = "Repeat"
                                textSize = 18f
                                setOnCheckedChangeListener { _, isChecked ->
                                    viewModel.setRepeatSelected(isChecked)
                                }
                            }
                            addView(
                                repeatSwitch,
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                                    setMargins(dip16, dip8, dip16, dip16)
                                }
                            )
                            cycleView = EditText(context).apply {
                                visibility = View.GONE
                                doOnTextChanged(viewModel::setCycle)
                            }
                            addView(
                                cycleView,
                                linearLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
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
        viewModel.showDateSelectionDialog.observe(this, ::showDateSelectionDialog)
        viewModel.refreshRecord.observe(this, ::refreshRecord)
    }

    private fun showDateSelectionDialog(date: Date) {
        val calendar = GregorianCalendar()
        calendar.time = date
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

    private fun refreshRecord(record: Record) {
        titleEditText.setText(record.title)
        descriptionEditText.setText(record.description)
        repeatSwitch.isChecked = record.repeat
        cycleView.setText(record.cycle)
    }
}