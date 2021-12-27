package com.example.prinventory_mvvm.ui.toner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentTonerCreateBinding
import com.example.prinventory_mvvm.databinding.FragmentTonerDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TonerDetailFragment : Fragment(R.layout.fragment_toner_detail) {

    companion object {
        const val BUNDLE_KEY = "toner"
    }

    private var _binding: FragmentTonerDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TonerViewModel by viewModels()
    private val args : TonerDetailFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTonerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonPress()
        val toner = args.toner

        binding.fragmentTonerDetailImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentTonerDetailImageviewDelete.setOnClickListener {
            viewModel.deleteToner(toner)
            findNavController().popBackStack()
        }

        binding.fragmentTonerDetailButtonEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, toner)
            }
            findNavController().navigate(R.id.action_tonerDetailFragment_to_tonerEditFragment, bundle)
        }

        val make = toner.make
        val color = toner.color
        val black = toner.black.toString()
        val cyan = toner.cyan.toString()
        val yellow = toner.yellow.toString()
        val magenta = toner.magenta.toString()

        viewModel.usesColor.observe(viewLifecycleOwner, {
            hideSpinner(it)
        })

        if(color == 0){
            viewModel.usesColor.postValue(false)
        }else{
            viewModel.usesColor.postValue(true)
        }

        binding.fragmentTonerDetailTextviewMake.text = make
        binding.fragmentTonerDetailTextviewBlack.text = black
        binding.fragmentTonerDetailTextviewCyan.text = cyan
        binding.fragmentTonerDetailTextviewYellow.text = yellow
        binding.fragmentTonerDetailTextviewMagenta.text = magenta
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun hideSpinner(usesColor: Boolean){
        if (usesColor){
            binding.fragmentTonerDetailFrameCyan.visibility = View.VISIBLE
            binding.fragmentTonerDetailFrameYellow.visibility = View.VISIBLE
            binding.fragmentTonerDetailFrameMagenta.visibility = View.VISIBLE
        }else{
            binding.fragmentTonerDetailFrameCyan.visibility = View.GONE
            binding.fragmentTonerDetailFrameYellow.visibility = View.GONE
            binding.fragmentTonerDetailFrameMagenta.visibility = View.GONE
        }
    }

}