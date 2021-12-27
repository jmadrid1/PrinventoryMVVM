package com.example.prinventory_mvvm.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.ActivityVendorBinding
import com.example.prinventory_mvvm.ui.printer.PrinterActivity
import com.example.prinventory_mvvm.ui.toner.TonerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VendorActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityVendorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.navHostFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vendorFragment -> { showToolbars() }
                R.id.vendorCreateFragment -> { hideToolbars() }
                R.id.vendorDetailFragment -> { hideToolbars() }
                R.id.vendorEditFragment -> { hideToolbars() }
            }
        }

        binding.activityToolbar.activityToolbarAdd.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.vendorCreateFragment)
        }

        binding.vendorBottomnavigation.menu.findItem(R.id.navigation_menu_vendor).isChecked = true
        binding.vendorBottomnavigation!!.setOnNavigationItemSelectedListener(this)
    }

    private fun hideToolbars(){
        binding.activityToolbar.root.visibility = View.GONE
        binding.vendorBottomnavigation.visibility = View.GONE
    }

    private fun showToolbars(){
        binding.activityToolbar.root.visibility = View.VISIBLE
        binding.vendorBottomnavigation.visibility = View.VISIBLE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_menu_printer -> { navigateToPrinters() }
            R.id.navigation_menu_toner -> { navigateToToners() }
            R.id.navigation_menu_vendor -> { }
        }
        return true
    }

    private fun navigateToPrinters(){
        val intent = Intent(this@VendorActivity, PrinterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun navigateToToners(){
        val intent = Intent(this@VendorActivity, TonerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

}