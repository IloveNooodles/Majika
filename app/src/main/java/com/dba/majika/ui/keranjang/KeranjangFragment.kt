package com.dba.majika.ui.keranjang

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dba.majika.PembayaranActivity
import com.dba.majika.adapters.KeranjangAdapter
import com.dba.majika.databinding.FragmentKeranjangBinding
import com.dba.majika.models.keranjang.KeranjangItem


class KeranjangFragment : Fragment() {
    
    companion object {
        fun newInstance() = KeranjangFragment()
    }
    
    private var _binding: FragmentKeranjangBinding? = null
    
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: KeranjangViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the view Model after onActivityCreated"
        }
        ViewModelProvider(this, KeranjangViewModel.Factory(activity.application)).get(
            KeranjangViewModel::class.java
        )
    }
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        binding.keranjangButtonBayar.setOnClickListener {
            val intent = Intent(context, PembayaranActivity::class.java)
            startActivity(intent)
        }
        
        val root: View = binding.root
        val recyclerView = binding.keranjangRecyclerView
        
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = KeranjangAdapter(viewModel)
        
        return root
    }
    
    override fun onStart() {
        super.onStart()
        viewModel.keranjangItems.observe(viewLifecycleOwner, Observer<List<KeranjangItem>> {
            it?.apply {
                (binding.keranjangRecyclerView.adapter as KeranjangAdapter).list = it
            }
        })
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
