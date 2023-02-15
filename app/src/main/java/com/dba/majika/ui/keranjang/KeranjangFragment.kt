package com.dba.majika.ui.keranjang

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dba.majika.R

class KeranjangFragment : Fragment() {

    companion object {
        fun newInstance() = KeranjangFragment()
    }

    private lateinit var viewModel: KeranjangViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_keranjang, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
