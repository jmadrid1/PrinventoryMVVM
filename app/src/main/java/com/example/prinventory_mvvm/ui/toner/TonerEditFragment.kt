package com.example.prinventory_mvvm.ui.toner

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.models.Toner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_toner_edit.*

@AndroidEntryPoint
class TonerEditFragment : Fragment(R.layout.fragment_toner_edit) {

    private val viewModel : TonerViewModel by viewModels()
    private val args : TonerEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toner = args.toner

        viewModel.usesColor.observe(viewLifecycleOwner, {
            hideSpinners(it)
        })

        initViews(toner)

        fragment_toner_edit_spinner_color.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                if(pos == 0){
                    viewModel.usesColor.postValue(false)
                }else{
                    viewModel.usesColor.postValue(true)
                }
            }
            override fun onNothingSelected(parent: AdapterView<out Adapter>?) { }
        }
    }

    private fun initViews(toner: Toner){
        onBackButtonPress()

        fragment_toner_edit_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_toner_edit_button_update.setOnClickListener {
            updateToner(toner)
        }

        val make = toner.make
        val model = toner.model
        val tonerModel = toner.tonerModel
        val color: Int = toner.color!!
        val black: Int = toner.black!!
        val cyan: Int = toner.cyan!!
        val yellow: Int = toner.yellow!!
        val magenta: Int = toner.magenta!!

        initSpinners(color, black, cyan, yellow, magenta)

        if(color == 0){
            viewModel.usesColor.postValue(false)
        }else{
            viewModel.usesColor.postValue(true)
        }

        fragment_toner_edit_edittext_make.setText(make)
        fragment_toner_edit_edittext_model.setText(model)
        fragment_toner_edit_edittext_tmodel.setText(tonerModel)
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(color: Int, black: Int, cyan: Int, yellow: Int, magenta: Int){
        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_toner_edit_spinner_color.adapter = colorAdapter
        fragment_toner_edit_spinner_color.setSelection(color)

        val tonerCountAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_toner_count, R.layout.spinner_dropdown_item)
        tonerCountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        fragment_toner_edit_spinner_black.adapter = tonerCountAdapter
        fragment_toner_edit_spinner_cyan.adapter = tonerCountAdapter
        fragment_toner_edit_spinner_yellow.adapter = tonerCountAdapter
        fragment_toner_edit_spinner_magenta.adapter = tonerCountAdapter

        fragment_toner_edit_spinner_black.setSelection(black)
        fragment_toner_edit_spinner_cyan.setSelection(cyan)
        fragment_toner_edit_spinner_yellow.setSelection(yellow)
        fragment_toner_edit_spinner_magenta.setSelection(magenta)
    }

    private fun updateToner(toner: Toner){
        checkTextField(fragment_toner_edit_edittext_make)
        checkTextField(fragment_toner_edit_edittext_model)
        checkTextField(fragment_toner_edit_edittext_tmodel)

        val make : String = fragment_toner_edit_edittext_make.text.toString()
        val model : String = fragment_toner_edit_edittext_model.text.toString()
        val tonerModel : String = fragment_toner_edit_edittext_tmodel.text.toString()
        val color : Int  = fragment_toner_edit_spinner_color.selectedItemPosition
        val black : Int  = fragment_toner_edit_spinner_black.selectedItemPosition
        val cyan : Int  = fragment_toner_edit_spinner_cyan.selectedItemPosition
        val yellow : Int  = fragment_toner_edit_spinner_yellow.selectedItemPosition
        val magenta : Int  = fragment_toner_edit_spinner_magenta.selectedItemPosition

        toner.make = make
        toner.model = model
        toner.tonerModel = tonerModel
        toner.color = color
        toner.black = black
        toner.cyan = cyan
        toner.yellow = yellow
        toner.magenta = magenta

        viewModel.insertToner(toner)
        findNavController().navigate(R.id.tonerFragment)
    }

    private fun hideSpinners(usesColor: Boolean){
        if (usesColor){
            fragment_toner_edit_textview_label_cyan.visibility = View.VISIBLE
            fragment_toner_edit_textview_label_yellow.visibility = View.VISIBLE
            fragment_toner_edit_textview_label_magenta.visibility = View.VISIBLE

            fragment_toner_edit_spinner_cyan.visibility = View.VISIBLE
            fragment_toner_edit_spinner_yellow.visibility = View.VISIBLE
            fragment_toner_edit_spinner_magenta.visibility = View.VISIBLE
        }else{
            fragment_toner_edit_textview_label_cyan.visibility = View.GONE
            fragment_toner_edit_textview_label_yellow.visibility = View.GONE
            fragment_toner_edit_textview_label_magenta.visibility = View.GONE

            fragment_toner_edit_spinner_cyan.visibility = View.GONE
            fragment_toner_edit_spinner_yellow.visibility = View.GONE
            fragment_toner_edit_spinner_magenta.visibility = View.GONE

            fragment_toner_edit_spinner_cyan.setSelection(0)
            fragment_toner_edit_spinner_yellow.setSelection(0)
            fragment_toner_edit_spinner_magenta.setSelection(0)
        }
    }

    private fun checkTextField(editText: EditText){
        val text: String = editText.text.toString()

        if(TextUtils.isEmpty(text)){
            editText.setText("Not Specified")
        }
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}