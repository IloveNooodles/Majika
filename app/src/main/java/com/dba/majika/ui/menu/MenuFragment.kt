package com.dba.majika.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dba.majika.adapters.MenuItemAdapter
import com.dba.majika.databinding.FragmentMenuBinding
import com.dba.majika.models.menu.MenuListItem

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MenuViewModel.Factory(activity.application))
            .get(MenuViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.menuRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MenuItemAdapter()

        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.menu.observe(viewLifecycleOwner, Observer<List<MenuListItem>> { item ->
            item?.apply {
                (binding.menuRecyclerView.adapter as MenuItemAdapter).list = item
            }
        })
    }

    override fun onStop(){
        super.onStop()
        viewModel.menu.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
