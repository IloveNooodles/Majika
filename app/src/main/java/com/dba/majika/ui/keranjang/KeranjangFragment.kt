package com.dba.majika.ui.keranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dba.majika.databinding.FragmentKeranjangBinding

class KeranjangFragment : Fragment() {
    
    companion object {
        fun newInstance() = KeranjangFragment()
    }
    
    private var _binding: FragmentKeranjangBinding? = null
    
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        return root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        // TODO: Use the ViewModel
    }
    
}
