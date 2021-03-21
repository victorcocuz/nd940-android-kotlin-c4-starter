package com.udacity.project4.locationreminders.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.util.DataGeneratorAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RemindersDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt
@get:Rule
var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersDao: RemindersDao
    private lateinit var db: RemindersDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RemindersDatabase::class.java).build()
        remindersDao = db.reminderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertReminderAndGetById() = runBlockingTest{
        // Given a reminder
        val reminder = DataGeneratorAndroidTest.getReminderDto1()
        remindersDao.saveReminder(reminder)

        // When the reminder is fetched from the database
        val loaded = remindersDao.getReminderById(reminder.id)
        val loadedFromRandomId = remindersDao.getReminderById(UUID.randomUUID().toString())

        // Then the loaded data contains the expected values
        assertThat(loaded as ReminderDTO, notNullValue())
        assertThat(loaded.id, `is`(reminder.id))
        assertThat(loaded.title, `is`(reminder.title))
        assertThat(loaded.description, `is`(reminder.description))
        assertThat(loaded.latitude, `is`(reminder.latitude))
        assertThat(loaded.longitude, `is`(reminder.longitude))

        assertThat(loadedFromRandomId, `is`(CoreMatchers.nullValue()))
    }

    @Test
    fun getReminders_success() = runBlockingTest {
        // Given two reminders
        val reminder1 = DataGeneratorAndroidTest.getReminderDto1()
        val reminder2 = DataGeneratorAndroidTest.getReminderDto2()
        remindersDao.saveReminder(reminder1)
        remindersDao.saveReminder(reminder2)

        // When all reminders are fetched from the database
        val loadedReminders = remindersDao.getReminders()

        // Then loaded data has the correct count
        assertThat(loadedReminders.count(), `is`(2))

        assertThat(loadedReminders.first().title, `is`(reminder1.title))
        assertThat(loadedReminders.first().description, `is`(reminder1.description))
        assertThat(loadedReminders.first().location, `is`(reminder1.location))

        assertThat(loadedReminders.last().title, `is`(reminder2.title))
        assertThat(loadedReminders.last().description, `is`(reminder2.description))
        assertThat(loadedReminders.last().location, `is`(reminder2.location))
    }

    @Test
    fun deleteAllReminders_success() = runBlockingTest {
        // Given two reminders
        val reminder1 = DataGeneratorAndroidTest.getReminderDto1()
        val reminder2 = DataGeneratorAndroidTest.getReminderDto2()
        remindersDao.saveReminder(reminder1)
        remindersDao.saveReminder(reminder2)


        // When all reminders are deleted
        remindersDao.deleteAllReminders()
        val loadedReminders = remindersDao.getReminders()

        // Then the database is empty
        assertThat(loadedReminders.count(), `is`(0))
    }
}