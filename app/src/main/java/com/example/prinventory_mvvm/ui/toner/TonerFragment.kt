package com.example.prinventory_mvvm.ui.toner

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.adapters.TonerAdapter
import com.example.prinventory_mvvm.models.Toner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_toner.*

@AndroidEntryPoint
class TonerFragment : Fragment(R.layout.fragment_toner) {

    companion object {
        const val BUNDLE_KEY = "toner"
    }

    private val viewModel : TonerViewModel by viewModels()
    private lateinit var tonerAdapter : TonerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onBackButtonPress()

        viewModel.getToners.observe(viewLifecycleOwner, {
            hideEmptyInventoryPrompts(it)
            tonerAdapter.differ.submitList(it)
        })

        tonerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, it)
            }
            findNavController().navigate(R.id.action_tonerFragment_to_tonerDetailFragment, bundle)
        }
    }

    private fun setupRecyclerView() = fragment_toner_recyclerview.apply{
        tonerAdapter = TonerAdapter()
        adapter = tonerAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun hideEmptyInventoryPrompts(toners: List<Toner>){
        if(toners.isEmpty()){
            fragment_toner_textview_empty_msg_1.visibility = View.VISIBLE
            fragment_toner_textview_empty_msg_2.visibility = View.VISIBLE
            fragment_toner_recyclerview.visibility = View.GONE
        }else {
            fragment_toner_textview_empty_msg_1.visibility = View.GONE
            fragment_toner_textview_empty_msg_2.visibility = View.GONE
            fragment_toner_recyclerview.visibility = View.VISIBLE
        }
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}