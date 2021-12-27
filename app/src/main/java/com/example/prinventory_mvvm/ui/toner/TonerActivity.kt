package com.example.prinventory_mvvm.ui.toner

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.ActivityTonerBinding
import com.example.prinventory_mvvm.ui.printer.PrinterActivity
import com.example.prinventory_mvvm.ui.vendor.VendorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TonerActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityTonerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTonerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.navHostFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.tonerFragment -> { showToolbars() }
                R.id.tonerCreateFragment -> { hideToolbars() }
                R.id.tonerDetailFragment -> { hideToolbars() }
                R.id.tonerEditFragment -> { hideToolbars() }
            }
        }

        binding.activityToolbar.activityToolbarAdd.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.tonerCreateFragment)
        }

        binding.tonerBottomnavigation.menu.findItem(R.id.navigation_menu_toner).isChecked = true
        binding.tonerBottomnavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun hideToolbars(){
        binding.activityToolbar.root.visibility = View.GONE
        binding.tonerBottomnavigation.visibility = View.GONE
    }

    private fun showToolbars(){
        binding.activityToolbar.root.visibility = View.VISIBLE
        binding.tonerBottomnavigation.visibility = View.VISIBLE
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