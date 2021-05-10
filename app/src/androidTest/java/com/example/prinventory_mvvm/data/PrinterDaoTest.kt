package com.example.prinventory_mvvm.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.prinventory_mvvm.data.database.Database
import com.example.prinventory_mvvm.data.repositories.PrinterDAO
import com.example.prinventory_mvvm.getOrAwaitValue
import com.example.prinventory_mvvm.models.Printer
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
class PrinterDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("prinventory_db")
    lateinit var database: Database
    private lateinit var printerDAO: PrinterDAO

    @Before
    fun setup() {
        hiltRule.inject()
        printerDAO = database.printerDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPrinter() = runBlockingTest {
        val printer = Printer(5, "Konica", "C368", "LDFNDFSLFKN", 1, 0, "Leased", "Business", "West Wing", "2nd", "23")
        printerDAO.insert(printer)

        val allPrinters = printerDAO.getAllPrinters().getOrAwaitValue()

        assertThat(allPrinters).contains(printer)
    }

    @Test
    fun deletePrinter() = runBlockingTest {
        val printer = Printer(2, "Ricoh", "5500", "DFSDFX", 1, 0, "Leased", "Engineering", "East Wing", "3rd", "86")

        printerDAO.insert(printer)
        printerDAO.delete(printer)

        val allPrinters = printerDAO.getAllPrinters().getOrAwaitValue()

        assertThat(allPrinters).doesNotContain(printer)
    }

//    @Test
//    fun observeTotalPriceSum() = runBlockingTest {
//        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", id = 1)
//        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", id = 2)
//        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", id = 3)
//        dao.insertShoppingItem(shoppingItem1)
//        dao.insertShoppingItem(shoppingItem2)
//        dao.insertShoppingItem(shoppingItem3)
//
//        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
//
//        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
//    }
}
