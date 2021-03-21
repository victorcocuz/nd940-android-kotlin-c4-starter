package com.udacity.project4.locationreminders

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

object DataGeneratorTest {

    fun getReminderDto1(): ReminderDTO {
        return ReminderDTO(
            title = "Title 1",
            description = "Description 1",
            location = "London",
            latitude = 51.50,
            longitude = -0.11
        )
    }

    fun getReminderDto2(): ReminderDTO {
        return ReminderDTO(
            title = "Title 2",
            description = "Description 2",
            location = "Toronto",
            latitude = 43.651070,
            longitude = -79.347015
        )
    }

    fun getValidReminderDataItem(): ReminderDataItem {
        return ReminderDataItem(
            title = "Title 1",
            description = "Description 1",
            location = "London",
            latitude = 51.50,
            longitude = -0.11
        )
    }
}
