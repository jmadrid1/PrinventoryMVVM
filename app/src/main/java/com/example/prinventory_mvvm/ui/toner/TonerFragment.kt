package com.example.prinventory_mvvm.ui.toner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.adapters.TonerAdapter
import com.example.prinventory_mvvm.databinding.FragmentPrinterCreateBinding
import com.example.prinventory_mvvm.databinding.FragmentTonerBinding
import com.example.prinventory_mvvm.models.Toner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TonerFragment : Fragment(R.layout.fragment_toner) {

    companion object {
        const val BUNDLE_KEY = "toner"
    }

    private var _binding: FragmentTonerBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TonerViewModel by viewModels()
    private lateinit var tonerAdapter : TonerAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTonerBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    private fun setupRecyclerView() = binding.fragmentTonerRecyclerview.apply{
        tonerAdapter = TonerAdapter()
        adapter = tonerAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun hideEmptyInventoryPrompts(toners: List<Toner>){
        if(toners.isEmpty()){
            binding.fragmentTonerTextviewEmptyMsg1.visibility = View.VISIBLE
            binding.fragmentTonerTextviewEmptyMsg2.visibility = View.VISIBLE
            binding.fragmentTonerRecyclerview.visibility = View.GONE
        }else {
            binding.fragmentTonerTextviewEmptyMsg1.visibility = View.GONE
            binding.fragmentTonerTextviewEmptyMsg2.visibility = View.GONE
            binding.fragmentTonerRecyclerview.visibility = View.VISIBLE
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