package com.dba.majika.ui.restoran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.R
import com.dba.majika.adapters.RestaurantAdapter
import com.dba.majika.data.RestoranDatasource
import com.dba.majika.databinding.FragmentRestoranBinding

class RestoranFragment : Fragment() {

    private var _binding: FragmentRestoranBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestoranBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        val myDataset = RestoranDatasource().loadRestorans()
        val recyclerView = binding.restoranRecyclerView
        recyclerView?.adapter = RestaurantAdapter(myDataset)
        recyclerView?.setHasFixedSize(true)
        
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
