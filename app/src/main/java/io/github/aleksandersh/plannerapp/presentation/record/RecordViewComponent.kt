package io.github.aleksandersh.plannerapp.presentation.record

import android.animation.LayoutTransition
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import io.github.aleksandersh.plannerapp.presentation.base.AnkoViewComponent
import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.utils.doOnTextChanged
import io.github.aleksandersh.plannerapp.utils.observeNonNull
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.switchCompat
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import java.util.*

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class RecordViewComponent(
    private val context: Context,
    private val viewModel: RecordViewScope
) : AnkoViewComponent<ViewGroup>(context) {

    private object ID {

        val editTextTitle = ViewCompat.generateViewId()
        val editTextDescription = ViewCompat.generateViewId()
        val switchRepeat = ViewCompat.generateViewId()
        val editTextCycle = ViewCompat.generateViewId()
    }

    private lateinit var dateButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var repeatSwitch: SwitchCompat
    private lateinit var cycleView: EditText

    override fun buildAnkoView(ui: AnkoContext<Context>): ViewGroup = with(ui) {
        val dip4 = dip(4)
        val dip8 = dip(8)
        val dip16 = dip(16)
        frameLayout {
            isClickable = true
            setBackgroundColor(Color.argb(220, 255, 255, 255))
            cardView {
                cardElevation = dip4.toFloat()
                radius = dip4.toFloat()
                scrollView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER
                        layoutTransition = LayoutTransition()
                        dateButton = button {
                            setOnClickListener { viewModel.onClickChooseDate() }
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip16, dip16, dip16, dip8)
                        }
                        textInputLayout {
                            hint = "Title"
                            titleEditText = textInputEditText {
                                id = ID.editTextTitle
                                doOnTextChanged(viewModel::setTitle)
                            }
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip16, dip16, dip16, dip8)
                        }
                        textInputLayout {
                            hint = "Description"
                            descriptionEditText = textInputEditText {
                                id = ID.editTextDescription
                                doOnTextChanged(viewModel::setDescription)
                            }
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip16, dip8, dip16, dip16)
                        }
                        repeatSwitch = switchCompat {
                            id = ID.switchRepeat
                            text = "Repeat"
                            textSize = 18f
                            setOnCheckedChangeListener { _, isChecked ->
                                viewModel.setRepeatSelected(isChecked)
                            }
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip16, dip8, dip16, dip16)
                        }
                        cycleView = editText {
                            id = ID.editTextCycle
                            visibility = View.GONE
                            doOnTextChanged(viewModel::setCycle)
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip16, dip8, dip16, dip16)
                        }
                        button("Save") {
                            textSize = 16f
                            setOnClickListener { viewModel.onClickSaveChanges() }
                        }.lparams(matchParent, wrapContent) {
                            setMargins(dip16, dip8, dip16, dip16)
                            gravity = Gravity.END
                        }
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, wrapContent) {
                setMargins(dip16, dip16, dip16, dip16)
                gravity = Gravity.CENTER
            }
        }
    }

    override fun onAttach() {
        observeNonNull(viewModel.dateTitle) { dateTitle ->
            dateButton.text = dateTitle
        }
        observeNonNull(viewModel.isCycleShowed) { isCycleShowed ->
            cycleView.visibility = if (isCycleShowed) View.VISIBLE else View.GONE
        }
        observeNonNull(viewModel.showDateSelectionDialog, ::showDateSelectionDialog)
        observeNonNull(viewModel.refreshRecord, ::refreshRecord)
        observeNonNull(viewModel.finish) {
            (context as Activity).onBackPressed()
        }
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