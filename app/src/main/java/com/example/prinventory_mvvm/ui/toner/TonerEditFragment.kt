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
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentTonerDetailBinding
import com.example.prinventory_mvvm.databinding.FragmentTonerEditBinding
import com.example.prinventory_mvvm.models.Toner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TonerEditFragment : Fragment(R.layout.fragment_toner_edit) {

    private var _binding: FragmentTonerEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel : TonerViewModel by viewModels()
    private val args : TonerEditFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTonerEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toner = args.toner

        viewModel.usesColor.observe(viewLifecycleOwner, {
            hideSpinners(it)
        })

        initViews(toner)

        binding.fragmentTonerEditSpinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        binding.fragmentTonerEditImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentTonerEditButtonUpdate.setOnClickListener {
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

        binding.fragmentTonerEditEdittextMake.setText(make)
        binding.fragmentTonerEditEdittextModel.setText(model)
        binding.fragmentTonerEditEdittextTmodel.setText(tonerModel)
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(color: Int, black: Int, cyan: Int, yellow: Int, magenta: Int){
        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentTonerEditSpinnerColor.adapter = colorAdapter
        binding.fragmentTonerEditSpinnerColor.setSelection(color)

        val tonerCountAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_toner_count, R.layout.spinner_dropdown_item)
        tonerCountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        binding.fragmentTonerEditSpinnerBlack.adapter = tonerCountAdapter
        binding.fragmentTonerEditSpinnerBlack.adapter = tonerCountAdapter
        binding.fragmentTonerEditSpinnerYellow.adapter = tonerCountAdapter
        binding.fragmentTonerEditSpinnerMagenta.adapter = tonerCountAdapter

        binding.fragmentTonerEditSpinnerBlack.setSelection(black)
        binding.fragmentTonerEditSpinnerCyan.setSelection(cyan)
        binding.fragmentTonerEditSpinnerYellow.setSelection(yellow)
        binding.fragmentTonerEditSpinnerMagenta.setSelection(magenta)
    }

    private fun updateToner(toner: Toner){
        checkTextField(binding.fragmentTonerEditEdittextMake)
        checkTextField(binding.fragmentTonerEditEdittextModel)
        checkTextField(binding.fragmentTonerEditEdittextTmodel)

        val make : String = binding.fragmentTonerEditEdittextMake.text.toString()
        val model : String = binding.fragmentTonerEditEdittextModel.text.toString()
        val tonerModel : String = binding.fragmentTonerEditEdittextTmodel.text.toString()
        val color : Int  = binding.fragmentTonerEditSpinnerColor.selectedItemPosition
        val black : Int  = binding.fragmentTonerEditSpinnerBlack.selectedItemPosition
        val cyan : Int  = binding.fragmentTonerEditSpinnerCyan.selectedItemPosition
        val yellow : Int  = binding.fragmentTonerEditSpinnerYellow.selectedItemPosition
        val magenta : Int  = binding.fragmentTonerEditSpinnerMagenta.selectedItemPosition

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
            binding.fragmentTonerEditTextviewLabelCyan.visibility = View.VISIBLE
            binding.fragmentTonerEditTextviewLabelYellow.visibility = View.VISIBLE
            binding.fragmentTonerEditTextviewLabelMagenta.visibility = View.VISIBLE

            binding.fragmentTonerEditSpinnerCyan.visibility = View.VISIBLE
            binding.fragmentTonerEditSpinnerYellow.visibility = View.VISIBLE
            binding.fragmentTonerEditSpinnerMagenta.visibility = View.VISIBLE
        }else{
            binding.fragmentTonerEditTextviewLabelCyan.visibility = View.GONE
            binding.fragmentTonerEditTextviewLabelYellow.visibility = View.GONE
            binding.fragmentTonerEditTextviewLabelMagenta.visibility = View.GONE

            binding.fragmentTonerEditSpinnerCyan.visibility = View.GONE
            binding.fragmentTonerEditSpinnerYellow.visibility = View.GONE
            binding.fragmentTonerEditSpinnerMagenta.visibility = View.GONE

            binding.fragmentTonerEditSpinnerCyan.setSelection(0)
            binding.fragmentTonerEditSpinnerYellow.setSelection(0)
            binding.fragmentTonerEditSpinnerMagenta.setSelection(0)
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