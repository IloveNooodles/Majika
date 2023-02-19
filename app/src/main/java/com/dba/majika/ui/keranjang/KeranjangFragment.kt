package com.dba.majika.ui.keranjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dba.majika.adapters.KeranjangAdapter
import com.dba.majika.data.KeranjangDataSource
import com.dba.majika.databinding.FragmentKeranjangBinding
import com.dba.majika.ui.pembayaran.PembayaranFragment


class KeranjangFragment : Fragment() {
    
    companion object {
        fun newInstance() = KeranjangFragment()
    }
    
    private var _binding: FragmentKeranjangBinding? = null
    
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    override fun toString(): String {
        return "navigation_keranjang"
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        binding.keranjangButtonBayar.setOnClickListener {
            fun onClick() {
                val fragmentManager = activity?.supportFragmentManager
                if (fragmentManager != null) {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val pembayaranFragment = PembayaranFragment()
                    fragmentTransaction.addToBackStack(this.toString())
                    fragmentTransaction.replace(this.id, pembayaranFragment)
                    fragmentTransaction.commit()
                }
                
            }
            onClick()
        }
        
        val root: View = binding.root
        val recylerView = binding.keranjangRecyclerView
        recylerView.layoutManager = LinearLayoutManager(activity)
        val data = KeranjangDataSource.loadMenu()
        recylerView.adapter = KeranjangAdapter(data)
        
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        // TODO: Use the ViewModel
    }
    
}
