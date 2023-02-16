package com.dba.majika.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dba.majika.adapters.RestaurantAdapter
import com.dba.majika.data.RestaurantDatasource
import com.dba.majika.databinding.FragmentRestaurantBinding
import com.dba.majika.models.restaurant.Restaurant

class RestaurantFragment : Fragment(), RestaurantListener {

    private var _binding: FragmentRestaurantBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        val root: View = binding.root
        RestaurantDatasource().loadRestaurants(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDataLoad(restaurants: List<Restaurant>) {
        val recyclerView = binding.restaurantRecyclerView
        recyclerView.adapter = RestaurantAdapter(requireContext(), restaurants)
        recyclerView.setHasFixedSize(true)
    }
}
