package com.dba.majika.ui.pembayaran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dba.majika.databinding.FragmentPembayaranBinding

class PembayaranFragment : Fragment() {
    
    companion object {
        fun newInstance() = PembayaranFragment()
    }
    
    private var _binding: FragmentPembayaranBinding? = null
    
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPembayaranBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        return root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        // TODO: Use the ViewModel
    }
    
}
