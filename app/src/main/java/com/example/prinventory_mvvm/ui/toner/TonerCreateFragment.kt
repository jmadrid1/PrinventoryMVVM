package com.example.prinventory_mvvm.ui.toner

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentPrinterDetailBinding
import com.example.prinventory_mvvm.databinding.FragmentTonerCreateBinding
import com.example.prinventory_mvvm.models.Toner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TonerCreateFragment : Fragment(R.layout.fragment_toner_create) {

    private var _binding: FragmentTonerCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TonerViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTonerCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonPress()
        initViews()

        binding.fragmentTonerCreateButtonSave.setOnClickListener {
            saveToner()
        }

        viewModel.usesColor.observe(viewLifecycleOwner, {
            hideSpinners(it)
        })

        binding.fragmentTonerCreateSpinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    private fun initViews(){
        binding.fragmentTonerCreateImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }
        initSpinners()
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(){
        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentTonerCreateSpinnerColor.adapter = colorAdapter
        binding.fragmentTonerCreateSpinnerColor.setSelection(0)

        val tonerCountAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_toner_count, R.layout.spinner_dropdown_item)
        tonerCountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        binding.fragmentTonerCreateSpinnerBlack.adapter = tonerCountAdapter
        binding.fragmentTonerCreateSpinnerCyan.adapter = tonerCountAdapter
        binding.fragmentTonerCreateSpinnerYellow.adapter = tonerCountAdapter
        binding.fragmentTonerCreateSpinnerMagenta.adapter = tonerCountAdapter

        binding.fragmentTonerCreateSpinnerBlack.setSelection(0)
        binding.fragmentTonerCreateSpinnerCyan.setSelection(0)
        binding.fragmentTonerCreateSpinnerYellow.setSelection(0)
        binding.fragmentTonerCreateSpinnerMagenta.setSelection(0)
    }

    private fun saveToner(){
        checkTextField(binding.fragmentTonerCreateEdittextMake)
        checkTextField(binding.fragmentTonerCreateEdittextModel)
        checkTextField(binding.fragmentTonerCreateEdittextTmodel)

        val make : String = binding.fragmentTonerCreateEdittextMake.text.toString()
        val model : String = binding.fragmentTonerCreateEdittextModel.text.toString()
        val tmodel : String = binding.fragmentTonerCreateEdittextTmodel.text.toString()
        val color : Int  = binding.fragmentTonerCreateSpinnerColor.selectedItemPosition
        val black : Int  = binding.fragmentTonerCreateSpinnerBlack.selectedItemPosition
        val cyan : Int  = binding.fragmentTonerCreateSpinnerCyan.selectedItemPosition
        val yellow : Int  = binding.fragmentTonerCreateSpinnerYellow.selectedItemPosition
        val magenta : Int  = binding.fragmentTonerCreateSpinnerMagenta.selectedItemPosition

        val toner = Toner(make = make,
                model = model,
                tonerModel = tmodel,
                color = color,
                black = black,
                cyan = cyan,
                yellow = yellow,
                magenta = magenta)

        viewModel.insertToner(toner)
        findNavController().popBackStack()
    }

    private fun hideSpinners(usesColor: Boolean){
        if (usesColor){
            binding.fragmentTonerCreateTextviewLabelCyan.visibility = View.VISIBLE
            binding.fragmentTonerCreateTextviewLabelYellow.visibility = View.VISIBLE
            binding.fragmentTonerCreateTextviewLabelMagenta.visibility = View.VISIBLE

            binding.fragmentTonerCreateSpinnerCyan.visibility = View.VISIBLE
            binding.fragmentTonerCreateSpinnerYellow.visibility = View.VISIBLE
            binding.fragmentTonerCreateSpinnerMagenta.visibility = View.VISIBLE
        }else{
            binding.fragmentTonerCreateTextviewLabelCyan.visibility = View.GONE
            binding.fragmentTonerCreateTextviewLabelYellow.visibility = View.GONE
            binding.fragmentTonerCreateTextviewLabelMagenta.visibility = View.GONE

            binding.fragmentTonerCreateSpinnerCyan.visibility = View.GONE
            binding.fragmentTonerCreateSpinnerYellow.visibility = View.GONE
            binding.fragmentTonerCreateSpinnerMagenta.visibility = View.GONE

            binding.fragmentTonerCreateSpinnerCyan.setSelection(0)
            binding.fragmentTonerCreateSpinnerYellow.setSelection(0)
            binding.fragmentTonerCreateSpinnerMagenta.setSelection(0)
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