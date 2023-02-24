package com.dba.majika.ui.pembayaran

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.dba.majika.MainActivity
import com.dba.majika.R
import com.dba.majika.api.RetrofitClient
import com.dba.majika.databinding.FragmentPembayaranBinding
import com.dba.majika.models.pembayaran.PembayaranResponse
import com.dba.majika.repository.KeranjangRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PembayaranFragment : Fragment() {
    companion object {
        fun newInstance() = PembayaranFragment()
    }
    
    private var _binding: FragmentPembayaranBinding? = null
    private var cameraPermission = false
    private lateinit var codeScanner: CodeScanner
    
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPembayaranBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        val extras = requireActivity().intent.extras
        if (extras != null) {
            val value = extras.getString("total")
            binding.pembayaranTotal.text = "Total: $value"
        }
        
        if (!wasCameraPermissionWasGiven()) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your app.
                    cameraPermission = true
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Camera cannot be accessed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.launch(Manifest.permission.CAMERA)
        }
        
        return root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        codeScanner = CodeScanner(requireContext(), binding.pembayaranQrCode)
        codeScanner.decodeCallback = DecodeCallback {
            RetrofitClient.api.postPayment(it.text).enqueue(
                object : Callback<PembayaranResponse> {
                    override fun onFailure(call: Call<PembayaranResponse>, t: Throwable) {
                        Log.d("retrofit", "qr error")
                        binding.pembayaranStatus.text = getText(R.string.payment_error)
                        binding.pembayaranStatusDescription.text =
                            getText(R.string.payment_error_network)
                        
                        // Restart
                        codeScanner.stopPreview()
                        codeScanner.startPreview()
                    }
                    
                    override fun onResponse(
                        call: Call<PembayaranResponse>,
                        response: Response<PembayaranResponse>
                    ) {
                        Log.d("retrofit", "qr successful")
                        val result = response.body()
                        if (result?.status == "SUCCESS") {
                            Log.d("payment", "success")
                            binding.pembayaranStatus.text = getText(R.string.payment_success)
                            binding.pembayaranStatusDescription.text =
                                getText(R.string.payment_payed)
                            codeScanner.releaseResources()
                            
                            // Empty keranjang
                            KeranjangRepository.deleteAll()
                            
                            // Redirect to main activity (menu)
                            
                            Handler(Looper.getMainLooper()).postDelayed({
                                try {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                    startActivity(intent)
                                } catch (e: Exception) {
                                    Log.e("E", e.toString())
                                }
                            }, 5000)
                            
                            Toast.makeText(
                                requireActivity(),
                                "Payment success, automated redirect to menu in 5 seconds",
                                Toast.LENGTH_SHORT
                            ).show()
                            
                        } else if (result?.status == "FAILED") {
                            Log.d("payment", "failed")
                            binding.pembayaranStatus.text = getText(R.string.payment_failed)
                            binding.pembayaranStatusDescription.text =
                                getText(R.string.payment_not_payed)
                            
                            // Restart
                            codeScanner.stopPreview()
                            codeScanner.startPreview()
                            
                        } else {
                            Log.d("payment", "bad request")
                            binding.pembayaranStatus.text = getText(R.string.payment_error)
                            binding.pembayaranStatusDescription.text =
                                getText(R.string.payment_error_qr)
                            
                            // Restart
                            codeScanner.stopPreview()
                            codeScanner.startPreview()
                        }
                    }
                }
            )
        }
    }
    
    private fun wasCameraPermissionWasGiven(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermission = true
            return true
        }
        return cameraPermission
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
