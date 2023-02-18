package com.dba.majika.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dba.majika.adapters.RestaurantAdapter
import com.dba.majika.databinding.FragmentRestaurantBinding
import com.dba.majika.models.restaurant.Restaurant

class RestaurantFragment : Fragment() {

    private var _binding: FragmentRestaurantBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: RestaurantViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, RestaurantViewModel.Factory(activity.application))
            .get(RestaurantViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.restaurantRecyclerView
        recyclerView.adapter = RestaurantAdapter(requireContext())

        return root
    }

    override fun onStart() {
        super.onStart()
        // recycler view observer
        viewModel.restaurants.observe(viewLifecycleOwner, Observer<List<Restaurant>> { item ->
            item?.apply {
                (binding.restaurantRecyclerView.adapter as RestaurantAdapter).list = item
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
