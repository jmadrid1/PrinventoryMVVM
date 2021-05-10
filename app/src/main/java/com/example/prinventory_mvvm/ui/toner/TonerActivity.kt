package com.example.prinventory_mvvm.ui.toner

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.ui.printer.PrinterActivity
import com.example.prinventory_mvvm.ui.vendor.VendorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_printer.*
import kotlinx.android.synthetic.main.activity_toner.*
import kotlinx.android.synthetic.main.activity_toner.activity_toolbar
import kotlinx.android.synthetic.main.activity_toner.navHostFragment
import kotlinx.android.synthetic.main.activity_toolbar.*

@AndroidEntryPoint
class TonerActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toner)

        activity_toolbar_add.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.tonerCreateFragment)
        }

        toner_bottomnavigation.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
                .addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.tonerFragment -> {
                            activity_toolbar.visibility = View.VISIBLE
                            toner_bottomnavigation.visibility = View.VISIBLE
                        }
                        R.id.tonerCreateFragment -> {
                            activity_toolbar.visibility = View.GONE
                            toner_bottomnavigation.visibility = View.GONE
                        }
                        R.id.tonerDetailFragment -> {
                            activity_toolbar.visibility = View.GONE
                            toner_bottomnavigation.visibility = View.GONE
                        }
                        R.id.tonerEditFragment -> {
                            activity_toolbar.visibility = View.GONE
                            toner_bottomnavigation.visibility = View.GONE
                        }
                    }
                }

        toner_bottomnavigation.menu.findItem(R.id.navigation_menu_toner).isChecked = true
        toner_bottomnavigation!!.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_printer -> {
                val intent = Intent(this@TonerActivity, PrinterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }

            R.id.navigation_menu_toner -> {  }

            R.id.navigation_menu_vendor -> {
                val intent = Intent(this@TonerActivity, VendorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
        return true
    }
}