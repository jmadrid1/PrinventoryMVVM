package com.example.prinventory_mvvm.ui.printer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.ui.toner.TonerActivity
import com.example.prinventory_mvvm.ui.vendor.VendorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_printer.*
import kotlinx.android.synthetic.main.activity_printer.activity_toolbar
import kotlinx.android.synthetic.main.activity_printer.navHostFragment
import kotlinx.android.synthetic.main.activity_toner.*
import kotlinx.android.synthetic.main.activity_toolbar.*

@AndroidEntryPoint
class PrinterActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer)

        printer_bottomnavigation.setupWithNavController(navHostFragment.findNavController())

        activity_toolbar_add.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.printerCreateFragment)
        }

        navHostFragment.findNavController()
                .addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.printerFragment -> {
                            activity_toolbar.visibility = View.VISIBLE
                            printer_bottomnavigation.visibility = View.VISIBLE
                        }
                        R.id.printerCreateFragment -> {
                            activity_toolbar.visibility = View.GONE
                            printer_bottomnavigation.visibility = View.GONE
                        }
                        R.id.printerDetailFragment -> {
                            activity_toolbar.visibility = View.GONE
                            printer_bottomnavigation.visibility = View.GONE
                        }
                        R.id.printerEditFragment -> {
                            activity_toolbar.visibility = View.GONE
                            printer_bottomnavigation.visibility = View.GONE
                        }
                    }
                }

        printer_bottomnavigation.menu.findItem(R.id.navigation_menu_printer).isChecked = true
        printer_bottomnavigation!!.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_printer -> {
            }

            R.id.navigation_menu_toner -> {
                val intent = Intent(this@PrinterActivity, TonerActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            R.id.navigation_menu_vendor -> {
                val intent = Intent(this@PrinterActivity, VendorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
        return true
    }

}

