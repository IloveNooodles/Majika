package com.dba.majika.ui.menu

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dba.majika.adapters.MenuItemAdapter
import com.dba.majika.databinding.FragmentMenuBinding
import com.dba.majika.models.keranjang.KeranjangItem
import com.dba.majika.models.menu.MenuListItem
import com.dba.majika.ui.keranjang.KeranjangViewModel

class MenuFragment : Fragment(), SensorEventListener {
    private lateinit var mSensorManager: SensorManager;
    private var mTempSensor: Sensor? = null
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
    
    private val keranjangViewModel: KeranjangViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, KeranjangViewModel.Factory(activity.application))
            .get(KeranjangViewModel::class.java)
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
        recyclerView.adapter = MenuItemAdapter(viewModel, keranjangViewModel)
        
        return root
    }
    
    override fun onStart() {
        super.onStart()
        // recycler view observer
        viewModel.menu.observe(viewLifecycleOwner, Observer<List<MenuListItem>> { item ->
            item?.apply {
                (binding.menuRecyclerView.adapter as MenuItemAdapter).list = item
            }
        })

        keranjangViewModel.keranjangItems.observe(viewLifecycleOwner, Observer<List<KeranjangItem>> { item ->
            item?.apply {
                (binding.menuRecyclerView.adapter as MenuItemAdapter).keranjangItemList = item
            }
        })
        
        // search view listener
        val searchView = binding.searchView
        searchView.clearFocus();
        searchView.setOnClickListener { searchView.isIconified = false }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchView", "submit")
                if (query != null) {
                    viewModel.filter.value = query
                }
                searchView.clearFocus();
                return true
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length == 0) {
                    viewModel.filter.value = newText
                    searchView.clearFocus();
                }
                Log.d("SearchView", "change")
                return false
            }
        })
        
        // initialize sensors
        mSensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
        // temp sensor
        if (mTempSensor != null) {
            mSensorManager.registerListener(this, mTempSensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Toast.makeText(context, "Temperature sensor unavailable", Toast.LENGTH_SHORT)
        }
    }
    
    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }
    
    override fun onStop() {
        super.onStop()
        viewModel.menu.removeObservers(viewLifecycleOwner)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    override fun onSensorChanged(p0: SensorEvent?) {
        val temp = p0!!.values[0]
        binding.tempDisplay.text = "$temp°c"
    }
    
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    
    }
}
