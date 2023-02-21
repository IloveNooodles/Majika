package com.dba.majika.ui.pembayaran

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.dba.majika.api.RetrofitClient
import com.dba.majika.databinding.FragmentPembayaranBinding
import com.dba.majika.models.PaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranFragment : Fragment() {
    companion object {
        fun newInstance() = PembayaranFragment()
    }
    
    private var _binding: FragmentPembayaranBinding? = null
    private lateinit var codeScanner: CodeScanner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPembayaranBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        codeScanner = CodeScanner(requireContext(), binding.pembayaranQrCode)
        codeScanner.decodeCallback = DecodeCallback {
            RetrofitClient.api.postPayment(it.text).enqueue(
                object : Callback<PaymentResponse> {
                    override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                        Log.d("retrofit", "menu error")
                    }

                    override fun onResponse(
                        call: Call<PaymentResponse>,
                        response: Response<PaymentResponse>
                    ) {
                        Log.d("retrofit", "menu successful")
                        val result = response.body()
                        if (result?.status == "SUCCESS") {
                            Log.d("payment", "success")
                        }
                        else if (result?.status == "FAILED") {
                            Log.d("payment", "failed")
                        }
                        else {
                            Log.d("payment", "bad request")
                        }
                    }
                }
            )
        }
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}
