package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = ListViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        tvStartTime = findViewById(R.id.tv_add_start_time)
        tvEndTime = findViewById(R.id.tv_add_end_time)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val edtCourse = findViewById<TextView>(R.id.add_ed_course)
                val edtLecturer = findViewById<TextView>(R.id.add_ed_lecturer)
                val edtNote = findViewById<TextView>(R.id.add_ed_note)
                val spinner = findViewById<Spinner>(R.id.spinner2)
                val course = edtCourse.text.toString().trim()
                val lecturer = edtLecturer.text.toString().trim()
                val note = edtNote.text.toString().trim()
                val day = spinner.selectedItemPosition
                val startTime = findViewById<TextView>(R.id.tv_add_start_time).text.toString()
                val endTime = findViewById<TextView>(R.id.tv_add_end_time).text.toString()

                if (course.isNotEmpty() && lecturer.isNotEmpty() && note.isNotEmpty()) {
                    viewModel.insertCourse(courseName = course, day = day, startTime = startTime, endTime = endTime, lecturer = lecturer, note = note)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.empty_list_message), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    fun showTimePickerStartTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "showTimePickerStartTime")
    }

    fun showTimePickerEndTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "showTimePickerEndTime")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            "showTimePickerStartTime" -> tvStartTime.text = dateFormat.format(calendar.time)
            "showTimePickerEndTime" -> tvEndTime.text = dateFormat.format(calendar.time)
            else -> {
            }
        }
    }
}