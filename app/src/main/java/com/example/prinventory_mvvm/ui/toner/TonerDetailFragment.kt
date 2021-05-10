package com.example.prinventory_mvvm.ui.toner

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_toner_detail.*

@AndroidEntryPoint
class TonerDetailFragment : Fragment(R.layout.fragment_toner_detail) {

    companion object {
        const val BUNDLE_KEY = "toner"
    }

    private val viewModel : TonerViewModel by viewModels()
    private val args : TonerDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonPress()
        val toner = args.toner

        fragment_toner_detail_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_toner_detail_imageview_delete.setOnClickListener {
            viewModel.deleteToner(toner)
            findNavController().popBackStack()
        }

        fragment_toner_detail_button_edit.setOnClickListener {
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

        fragment_toner_detail_textview_make.text = make
        fragment_toner_detail_textview_black.text = black
        fragment_toner_detail_textview_cyan.text = cyan
        fragment_toner_detail_textview_yellow.text = yellow
        fragment_toner_detail_textview_magenta.text = magenta
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
            fragment_toner_detail_frame_cyan.visibility = View.VISIBLE
            fragment_toner_detail_frame_yellow.visibility = View.VISIBLE
            fragment_toner_detail_frame_magenta.visibility = View.VISIBLE
        }else{
            fragment_toner_detail_frame_cyan.visibility = View.GONE
            fragment_toner_detail_frame_yellow.visibility = View.GONE
            fragment_toner_detail_frame_magenta.visibility = View.GONE
        }
    }

}