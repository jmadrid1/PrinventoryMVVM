package com.example.prinventory_mvvm.ui.printer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.ActivityPrinterBinding
import com.example.prinventory_mvvm.ui.toner.TonerActivity
import com.example.prinventory_mvvm.ui.vendor.VendorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrinterActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityPrinterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrinterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.navHostFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.printerFragment -> { showToolbars() }
                R.id.printerCreateFragment -> { hideToolbars() }
                R.id.printerDetailFragment -> { hideToolbars() }
                R.id.printerEditFragment -> { hideToolbars() }
            }
        }

        binding.activityToolbar.activityToolbarAdd.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.printerCreateFragment)
        }

        binding.printerBottomnavigation.menu.findItem(R.id.navigation_menu_printer).isChecked = true
        binding.printerBottomnavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun hideToolbars(){
        binding.activityToolbar.root.visibility = View.GONE
        binding.printerBottomnavigation.visibility = View.GONE
    }

    private fun showToolbars(){
        binding.activityToolbar.root.visibility = View.VISIBLE
        binding.printerBottomnavigation.visibility = View.VISIBLE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_printer -> { }
            R.id.navigation_menu_toner -> { navigateToToners() }
            R.id.navigation_menu_vendor -> { navigateToVendors() }
        }
        return true
    }

    private fun navigateToToners(){
        val intent = Intent(this@PrinterActivity, TonerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun navigateToVendors(){
        val intent = Intent(this@PrinterActivity, VendorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

}

