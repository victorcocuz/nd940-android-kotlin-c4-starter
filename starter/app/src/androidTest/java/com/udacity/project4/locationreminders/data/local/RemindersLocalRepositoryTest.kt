package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.util.DataGeneratorAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class RemindersLocalRepositoryTest {

//    TODO: Add testing implementation to the RemindersLocalRepository.kt

    private lateinit var database: RemindersDatabase
    private lateinit var remindersRepository: RemindersLocalRepository

    private lateinit var reminder: ReminderDTO

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        remindersRepository = RemindersLocalRepository(database.reminderDao(), Dispatchers.Main)

        reminder = DataGeneratorAndroidTest.getReminderDto1()
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveReminder_retrievesReminder() = runBlocking {
        // Given a new reminder saved in the database.
        remindersRepository.saveReminder(reminder)

        // When reminder is retrieved by ID.
        val result = remindersRepository.getReminder(reminder.id) as Result.Success
        val data = result.data

        // Then the same reminder is returned.
        MatcherAssert.assertThat(data.id, CoreMatchers.`is`(reminder.id))
        MatcherAssert.assertThat(data.title, CoreMatchers.`is`(reminder.title))
        MatcherAssert.assertThat(data.description, CoreMatchers.`is`(reminder.description))
        MatcherAssert.assertThat(data.location, CoreMatchers.`is`(reminder.location))
        MatcherAssert.assertThat(data.latitude, CoreMatchers.`is`(reminder.latitude))
        MatcherAssert.assertThat(data.longitude, CoreMatchers.`is`(reminder.longitude))
    }

    @Test
    fun deleteReminders_returnsEmptyList() = runBlocking {
        remindersRepository.saveReminder(reminder)
        var reminders = remindersRepository.getReminders() as Result.Success
        MatcherAssert.assertThat(reminders.data, CoreMatchers.hasItem(reminder))

        remindersRepository.deleteAllReminders()
        reminders = remindersRepository.getReminders() as Result.Success
        MatcherAssert.assertThat(reminders.data.isEmpty(), CoreMatchers.`is`(true))
    }

    @Test
    fun getUnsavedReminder_returnsError() = runBlocking {
        val unsavedReminder = remindersRepository.getReminder(reminder.id) as Result.Error
        MatcherAssert.assertThat(unsavedReminder.message, CoreMatchers.`is`("Reminder not found!"))
    }
}