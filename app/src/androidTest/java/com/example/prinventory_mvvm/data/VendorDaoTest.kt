package com.example.prinventory_mvvm.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.prinventory_mvvm.data.database.Database
import com.example.prinventory_mvvm.data.repositories.VendorDAO
import com.example.prinventory_mvvm.models.Vendor
import com.example.prinventory_mvvm.getOrAwaitValue
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
class VendorDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("prinventory_db")
    lateinit var database: Database
    private lateinit var vendorDAO: VendorDAO

    @Before
    fun setup() {
        hiltRule.inject()
        vendorDAO = database.vendorDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertVendor() = runBlockingTest {
        val vendor = Vendor(5, "Nikola, Inc", "3230000000", "tesla@nikola.com", "Electric Ave", "Los Angeles", "CA", "91006")
        vendorDAO.insert(vendor)

        val allVendors = vendorDAO.getAllVendors().getOrAwaitValue()

        assertThat(allVendors).contains(vendor)
    }

    @Test
    fun deleteVendor() = runBlockingTest {
        val vendor = Vendor(5, "Nikola, Inc", "3230000000", "tesla@nikola.com", "Electric Ave", "Los Angeles", "CA", "91006")

        vendorDAO.insert(vendor)
        vendorDAO.delete(vendor)

        val allVendors = vendorDAO.getAllVendors().getOrAwaitValue()

        assertThat(allVendors).doesNotContain(vendor)
    }
}
