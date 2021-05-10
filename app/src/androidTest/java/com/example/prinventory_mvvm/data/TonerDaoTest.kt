package com.example.prinventory_mvvm.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.prinventory_mvvm.data.database.Database
import com.example.prinventory_mvvm.data.repositories.TonerDAO
import com.example.prinventory_mvvm.getOrAwaitValue
import com.example.prinventory_mvvm.models.Toner
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class TonerDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("prinventory_db")
    lateinit var database: Database
    private lateinit var tonerDAO: TonerDAO

    @Before
    fun setup() {
        hiltRule.inject()
        tonerDAO = database.tonerDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertToner() = runBlockingTest {
        val toner = Toner(1, "Brother", "MFC5300", "T5300", 0, 3, 0, 0, 0)
        tonerDAO.insert(toner)

        val allToners = tonerDAO.getAllToners().getOrAwaitValue()

        assertThat(allToners).contains(toner)
    }

    @Test
    fun deleteToner() = runBlockingTest {
        val toner = Toner(1, "Brother", "MFC5300", "T5300", 0, 3, 0, 0, 0)

        tonerDAO.insert(toner)
        tonerDAO.delete(toner)

        val allToners = tonerDAO.getAllToners().getOrAwaitValue()

        assertThat(allToners).doesNotContain(toner)
    }
}
