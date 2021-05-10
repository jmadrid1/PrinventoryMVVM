package com.example.prinventory_mvvm.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.ui.printer.PrinterActivity
import com.example.prinventory_mvvm.ui.toner.TonerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_printer.*
import kotlinx.android.synthetic.main.activity_toner.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.activity_vendor.*
import kotlinx.android.synthetic.main.activity_vendor.activity_toolbar
import kotlinx.android.synthetic.main.activity_vendor.navHostFragment

@AndroidEntryPoint
class VendorActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor)

        activity_toolbar_add.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.vendorCreateFragment)
        }

        vendor_bottomnavigation.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
                .addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.vendorFragment -> {
                            activity_toolbar.visibility = View.VISIBLE
                            vendor_bottomnavigation.visibility = View.VISIBLE
                        }
                        R.id.vendorCreateFragment -> {
                            activity_toolbar.visibility = View.GONE
                            vendor_bottomnavigation.visibility = View.GONE
                        }
                        R.id.vendorDetailFragment -> {
                            activity_toolbar.visibility = View.GONE
                            vendor_bottomnavigation.visibility = View.GONE
                        }
                        R.id.vendorEditFragment -> {
                            activity_toolbar.visibility = View.GONE
                            vendor_bottomnavigation.visibility = View.GONE
                        }
                    }
                }

        vendor_bottomnavigation.menu.findItem(R.id.navigation_menu_vendor).isChecked = true
        vendor_bottomnavigation!!.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_printer -> {
                val intent = Intent(this@VendorActivity, PrinterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }


            R.id.navigation_menu_toner -> {
                val intent = Intent(this@VendorActivity, TonerActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            R.id.navigation_menu_vendor -> {   }
        }
        return true
    }

}