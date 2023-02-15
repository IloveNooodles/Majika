package com.dba.majika.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.R
import com.dba.majika.adapters.MenuItemAdapter
import com.dba.majika.data.MenuDataSource
import com.dba.majika.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.menuRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MenuItemAdapter(MenuDataSource().loadMenu())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
