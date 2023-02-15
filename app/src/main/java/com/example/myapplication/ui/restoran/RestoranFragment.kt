package com.example.myapplication.ui.restoran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.RestaurantAdapter
import com.example.myapplication.data.RestoranDatasource
import com.example.myapplication.databinding.FragmentRestoranBinding

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
//        val dashboardViewModel =
//            ViewModelProvider(this).get(RestoranViewModel::class.java)

        _binding = FragmentRestoranBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Test data
        val myDataset = RestoranDatasource().loadRestorans()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.restoran_recycler_view)
        recyclerView?.adapter = RestaurantAdapter(myDataset)
        recyclerView?.setHasFixedSize(true)

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
